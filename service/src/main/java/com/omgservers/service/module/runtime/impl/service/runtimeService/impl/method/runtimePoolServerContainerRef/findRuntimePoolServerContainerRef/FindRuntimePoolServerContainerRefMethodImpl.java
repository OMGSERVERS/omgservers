package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.findRuntimePoolServerContainerRef;

import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.selectRuntimePoolServerContainerRefByRuntimeId.SelectRuntimePoolServerContainerRefByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimePoolServerContainerRefMethodImpl implements FindRuntimePoolServerContainerRefMethod {

    final SelectRuntimePoolServerContainerRefByRuntimeIdOperation
            selectRuntimePoolServerContainerRefByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            final FindRuntimePoolServerContainerRefRequest request) {
        log.debug("Find runtime pool server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectRuntimePoolServerContainerRefByRuntimeIdOperation
                                    .selectRuntimePoolServerContainerRefByRuntimeId(sqlConnection,
                                            shard.shard(),
                                            runtimeId));
                })
                .map(FindRuntimePoolServerContainerRefResponse::new);
    }
}
