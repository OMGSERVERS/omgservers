package com.omgservers.module.script.impl.service.scriptService.impl.method.getScript;

import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class
GetScriptMethodImpl implements GetScriptMethod {

    final SelectScriptOperation selectScriptOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetScriptResponse> getScript(GetScriptRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectScriptOperation
                            .selectScript(sqlConnection, shard.shard(), id));
                })
                .map(GetScriptResponse::new);
    }
}
