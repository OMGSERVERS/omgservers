package com.omgservers.module.script.impl.service.scriptService.impl.method.callScript;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.module.script.impl.operation.getLuaInstance.GetLuaInstanceOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<CallScriptResponse> callScript(CallScriptRequest request) {
        CallScriptRequest.validate(request);

        final var scriptId = request.getScriptId();
        final var events = request.getEvents();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection -> callScript(
                        sqlConnection, shardModel.shard(), scriptId, events)))
                .map(CallScriptResponse::new);
    }

    Uni<Boolean> callScript(final SqlConnection sqlConnection,
                            final int shard,
                            final Long scriptId,
                            final List<ScriptEventModel> scriptEvents) {
        return selectScriptOperation.selectScript(sqlConnection, shard, scriptId)
                .flatMap(getLuaInstanceOperation::getLuaInstance)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(luaInstance -> {
                    scriptEvents.forEach(scriptEvent -> {
                        final var luaEvent = mapScriptEventOperation.mapScriptEvent(scriptEvent);
                        luaInstance.handle(luaEvent);
                    });
                })
                .replaceWith(true);
    }
}
