package com.omgservers.module.script.impl.service.scriptService.impl.method.callScript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.module.script.impl.operation.getLuaInstance.CreateLuaInstanceOperation;
import com.omgservers.module.script.impl.operation.mapScriptEvent.MapScriptEventOperation;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import com.omgservers.module.script.impl.operation.updateScriptState.UpdateScriptStateOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CallScriptMethodImpl implements CallScriptMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpdateScriptStateOperation updateScriptStateOperation;
    final CreateLuaInstanceOperation createLuaInstanceOperation;
    final MapScriptEventOperation mapScriptEventOperation;
    final SelectScriptOperation selectScriptOperation;
    final CheckShardOperation checkShardOperation;

    final ObjectMapper objectMapper;
    final PgPool pgPool;

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        final var scriptId = request.getScriptId();
        final var events = request.getEvents();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                            (changeContext, sqlConnection) ->
                                    selectScriptOperation.selectScript(sqlConnection, shard, scriptId)
                                            .flatMap(script -> callScript(script, events))
                                            .flatMap(finalState -> updateScriptStateOperation.updateScriptState(
                                                    changeContext, sqlConnection, shard,
                                                    scriptId, finalState))
                    );
                })
                .replaceWith(true)
                .map(CallScriptResponse::new);
    }

    Uni<String> callScript(final ScriptModel script,
                           final List<ScriptEventModel> scriptEvents) {
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

                    final var luaEvents = scriptEvents.stream()
                            .map(mapScriptEventOperation::mapScriptEvent)
                            .toList();

                    log.info("Call script, scriptId={}, events={}", script.getId(), luaEvents);
                    luaEvents.forEach(luaEvent -> luaInstance.callScript(luaState, luaEvent));

                    final String finalState;
                    try {
                        finalState = objectMapper.writeValueAsString(luaState);
                    } catch (IOException e) {
                        throw new ServerSideConflictException("final script state can't be parsed" +
                                "script=" + script, e);
                    }

                    log.info("Script finished, scriptId={}, finalState={}", script.getId(), finalState);

                    return finalState;
                });
    }
}
