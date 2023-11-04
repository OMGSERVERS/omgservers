package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeGrant;

import com.omgservers.model.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrantByRuntimeIdAndEntityId.SelectRuntimeGrantByRuntimeIdAndEntityIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimeGrantMethodImpl implements FindRuntimeGrantMethod {

    final SelectRuntimeGrantByRuntimeIdAndEntityIdOperation selectRuntimeGrantByRuntimeIdAndEntityIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimeGrantResponse> findRuntimeGrant(final FindRuntimeGrantRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var entityId = request.getEntityId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeGrantByRuntimeIdAndEntityIdOperation
                            .selectRuntimeGrantByRuntimeIdAndEntityId(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    entityId));
                })
                .map(FindRuntimeGrantResponse::new);
    }
}
