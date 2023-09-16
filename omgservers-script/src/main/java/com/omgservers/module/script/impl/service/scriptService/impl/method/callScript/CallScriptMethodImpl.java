package com.omgservers.module.script.impl.service.scriptService.impl.method.callScript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.operation.getLuaInstance.GetLuaInstanceOperation;
import com.omgservers.module.script.impl.operation.getLuaInstance.LuaInstance;
import com.omgservers.module.script.impl.operation.mapScriptEvent.MapScriptEventOperation;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import com.omgservers.module.script.impl.service.scriptService.impl.cache.LuaInstanceCache;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CallScriptMethodImpl implements CallScriptMethod {

    final GetLuaInstanceOperation getLuaInstanceOperation;
    final MapScriptEventOperation mapScriptEventOperation;
    final SelectScriptOperation selectScriptOperation;
    final CheckShardOperation checkShardOperation;
    final LuaInstanceCache cache;

    final ObjectMapper objectMapper;
    final PgPool pgPool;

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        final var scriptId = request.getScriptId();
        final var events = request.getEvents();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection -> callScript(
                        sqlConnection,
                        shardModel.shard(),
                        scriptId,
                        events))
                )
                .map(CallScriptResponse::new);
    }

    Uni<Boolean> callScript(final SqlConnection sqlConnection,
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
                            return handleEvents(luaInstance, luaEvents);
                        }))
                .replaceWith(true);
    }

    Uni<Void> handleEvents(final LuaInstance luaInstance,
                           final List<LuaEvent> luaEvents) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> luaEvents.forEach(luaInstance::handle));
    }
}
