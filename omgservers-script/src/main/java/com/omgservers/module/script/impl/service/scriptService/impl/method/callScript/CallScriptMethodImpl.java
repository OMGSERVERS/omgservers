package com.omgservers.module.script.impl.service.scriptService.impl.method.callScript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.module.script.impl.operation.getLuaInstance.CreateLuaInstanceOperation;
import com.omgservers.module.script.impl.operation.mapLuaCommand.MapLuaCommandOperation;
import com.omgservers.module.script.impl.operation.mapScriptRequest.MapScriptRequestOperation;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import com.omgservers.module.script.impl.operation.updateScriptState.UpdateScriptStateOperation;
import com.omgservers.module.system.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CallScriptMethodImpl implements CallScriptMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpdateScriptStateOperation updateScriptStateOperation;
    final CreateLuaInstanceOperation createLuaInstanceOperation;
    final MapScriptRequestOperation mapScriptRequestOperation;
    final MapLuaCommandOperation mapLuaCommandOperation;
    final SelectScriptOperation selectScriptOperation;
    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;

    final ObjectMapper objectMapper;
    final PgPool pgPool;

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        final var scriptId = request.getScriptId();
        final var requests = request.getRequests();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Void>changeWithContext(
                            (changeContext, sqlConnection) ->
                                    selectScriptOperation.selectScript(sqlConnection, shard, scriptId)
                                            .flatMap(script -> callScript(script, requests)
                                                    // Step 1. Update script state
                                                    .call(result -> {
                                                        final String finalState;
                                                        try {
                                                            finalState = objectMapper
                                                                    .writeValueAsString(result.luaState);
                                                        } catch (IOException e) {
                                                            throw new ServerSideConflictException(
                                                                    "final script state can't be parsed" +
                                                                            "script=" + script, e);
                                                        }

                                                        return updateScriptStateOperation.updateScriptState(
                                                                changeContext, sqlConnection, shard,
                                                                scriptId, finalState);
                                                    })
                                                    // Step 2. Sync script events
                                                    .call(result -> Multi.createFrom().iterable(result.events)
                                                            .onItem().transformToUniAndConcatenate(event ->
                                                                    upsertEventOperation
                                                                            .upsertEvent(changeContext,
                                                                                    sqlConnection,
                                                                                    event))
                                                            .collect().asList()
                                                            .replaceWithVoid()
                                                    )
                                                    .replaceWithVoid()
                                            )
                    );
                })
                .replaceWith(true)
                .map(CallScriptResponse::new);
    }

    Uni<CallScriptResult> callScript(final ScriptModel script,
                                     final List<ScriptRequestModel> scriptRequests) {
        return createLuaInstanceOperation.createLuaInstance(script)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(luaInstance -> {
                    final LuaValue luaState;
                    try {
                        luaState = objectMapper.readValue(script.getState(), LuaValue.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException("script state can't be parsed, " +
                                "script=" + script, e);
                    }

                    final var luaRequests = scriptRequests.stream()
                            .map(mapScriptRequestOperation::mapScriptRequest)
                            .toList();

                    final var scriptEvents = new ArrayList<EventModel>();
                    for (final var luaRequest : luaRequests) {
                        if (luaRequest.getInfoLogging()) {
                            log.info("Call script, " +
                                            "type={}, " +
                                            "request={}, " +
                                            "id={}, " +
                                            "runtimeId={}, " +
                                            "tenantId={}, " +
                                            "versionId={}",
                                    script.getType(),
                                    luaRequest,
                                    script.getId(),
                                    script.getRuntimeId(),
                                    script.getTenantId(),
                                    script.getVersionId());
                        }

                        final var returnValue = luaInstance.callScript(luaState, luaRequest);
                        if (returnValue != LuaValue.NIL) {
                            final var returnTable = returnValue.checktable();
                            final var luaCommands = parseReturnValue(returnTable);
                            final var callEvents = luaCommands.stream()
                                    .map(luaCommand -> mapLuaCommandOperation
                                            .mapLuaCommand(script, luaCommand))
                                    .peek(event -> {
                                        final var qualifier = event.getQualifier();
                                        if (qualifier.getInfoLogging()) {
                                            log.info("Script produced event, " +
                                                            "qualifier={}, " +
                                                            "id={}, " +
                                                            "body={}",
                                                    qualifier,
                                                    event.getId(),
                                                    event.getBody());
                                        }
                                    })
                                    .toList();
                            scriptEvents.addAll(callEvents);
                        }
                    }

                    return new CallScriptResult(luaState, scriptEvents);
                });
    }

    List<LuaTable> parseReturnValue(LuaTable returnValue) {
        final var results = new ArrayList<LuaTable>();

        var k = LuaValue.NIL;
        while (true) {
            final var n = returnValue.next(k);
            k = n.arg1();
            if (k.isnil()) {
                break;
            }
            final var v = n.arg(2).checktable();

            results.add(v);
        }

        return results;
    }

    record CallScriptResult(LuaValue luaState, List<EventModel> events) {
    }
}
