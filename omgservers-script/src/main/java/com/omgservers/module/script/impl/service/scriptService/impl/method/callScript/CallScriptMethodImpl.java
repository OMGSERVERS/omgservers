package com.omgservers.module.script.impl.service.scriptService.impl.method.callScript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.operation.getLuaInstance.GetLuaInstanceOperation;
import com.omgservers.module.script.impl.operation.getLuaInstance.LuaInstance;
import com.omgservers.module.script.impl.operation.mapScriptEvent.MapScriptEventOperation;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import com.omgservers.module.script.impl.operation.updateScriptSelf.UpdateScriptSelfOperation;
import com.omgservers.module.script.impl.service.scriptService.impl.cache.LuaInstanceCache;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
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
    final UpdateScriptSelfOperation updateScriptSelfOperation;
    final GetLuaInstanceOperation getLuaInstanceOperation;
    final MapScriptEventOperation mapScriptEventOperation;
    final SelectScriptOperation selectScriptOperation;
    final CheckShardOperation checkShardOperation;
    final LuaInstanceCache cache;

    final ObjectMapper objectMapper;
    final PgPool pgPool;

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        CallScriptRequest.validate(request);

        final var scriptId = request.getScriptId();
        final var events = request.getEvents();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> callScript(
                                        changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        scriptId,
                                        events))
                        .map(ChangeContext::getResult)
                )
                .map(CallScriptResponse::new);
    }

    Uni<Boolean> callScript(final ChangeContext<?> changeContext,
                            final SqlConnection sqlConnection,
                            final int shard,
                            final Long scriptId,
                            final List<ScriptEventModel> scriptEvents) {
        return selectScriptOperation.selectScript(sqlConnection, shard, scriptId)
                .flatMap(script -> getLuaInstanceOperation.getLuaInstance(script)
                        .flatMap(luaInstance -> {
                            final var luaEvents = scriptEvents.stream()
                                    .map(mapScriptEventOperation::mapScriptEvent)
                                    .toList();
                            log.info("Call script, events={}, luaInstance={}, script={}",
                                    luaEvents, luaInstance, script);
                            return handleEvents(script, luaInstance, luaEvents);
                        }))
                .flatMap(luaSelf -> {
                    final String selfString;
                    try {
                        selfString = objectMapper.writeValueAsString(luaSelf);
                        log.info("Final lua self, self={}", selfString);
                    } catch (IOException e) {
                        throw new ServerSideConflictException("final lua self can't be parsed, luaSelf={}" + luaSelf, e);
                    }

                    return updateScriptSelfOperation.updateScriptSelf(
                            changeContext,
                            sqlConnection,
                            shard,
                            scriptId,
                            selfString);
                })
                .replaceWith(true);
    }

    Uni<LuaValue> handleEvents(final ScriptModel script,
                               final LuaInstance luaInstance,
                               final List<LuaEvent> luaEvents) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    try {
                        final var luaSelf = objectMapper.readValue(script.getSelf(), LuaValue.class);
                        luaEvents.forEach(luaEvent -> luaInstance.handle(luaSelf, luaEvent));
                        return luaSelf;
                    } catch (IOException e) {
                        throw new ServerSideConflictException("script self can't be parsed, script=" + script, e);
                    }
                });
    }
}
