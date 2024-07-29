package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.getRuntimePoolServerContainerRef;

import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.selectRuntimePoolServerContainerRef.SelectRuntimePoolServerContainerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimePoolServerContainerRefMethodImpl implements GetRuntimePoolServerContainerRefMethod {

    final SelectRuntimePoolServerContainerRefOperation selectRuntimePoolServerContainerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            final GetRuntimePoolServerContainerRefRequest request) {
        log.debug("Get runtime pool server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimePoolServerContainerRefOperation
                            .selectRuntimePoolServerContainerRef(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    id));
                })
                .map(GetRuntimePoolServerContainerRefResponse::new);
    }
}
