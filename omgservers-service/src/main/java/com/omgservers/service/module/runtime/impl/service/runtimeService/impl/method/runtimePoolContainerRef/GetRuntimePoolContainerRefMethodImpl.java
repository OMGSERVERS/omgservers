package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePoolContainerRef.SelectRuntimePoolContainerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimePoolContainerRefMethodImpl implements GetRuntimePoolContainerRefMethod {

    final SelectRuntimePoolContainerRefOperation selectRuntimePoolContainerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimePoolContainerRefResponse> execute(
            final GetRuntimePoolContainerRefRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimePoolContainerRefOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    id));
                })
                .map(GetRuntimePoolContainerRefResponse::new);
    }
}
