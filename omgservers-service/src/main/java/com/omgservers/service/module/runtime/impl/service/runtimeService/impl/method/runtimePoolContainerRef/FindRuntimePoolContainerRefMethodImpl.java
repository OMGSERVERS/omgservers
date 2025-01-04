package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePoolContainerRef.SelectRuntimePoolContainerRefByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimePoolContainerRefMethodImpl implements FindRuntimePoolContainerRefMethod {

    final SelectRuntimePoolContainerRefByRuntimeIdOperation
            selectRuntimePoolContainerRefByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimePoolContainerRefResponse> execute(
            final FindRuntimePoolContainerRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectRuntimePoolContainerRefByRuntimeIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            runtimeId));
                })
                .map(FindRuntimePoolContainerRefResponse::new);
    }
}
