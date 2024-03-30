package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.selectRuntimeServerContainerRefByRuntimeId.SelectRuntimeServerContainerRefByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimeServerContainerRefMethodImpl implements FindRuntimeServerContainerRefMethod {

    final SelectRuntimeServerContainerRefByRuntimeIdOperation selectRuntimeServerContainerRefByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimeServerContainerRefResponse> findRuntimeServerContainerRef(
            final FindRuntimeServerContainerRefRequest request) {
        log.debug("Find runtime server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectRuntimeServerContainerRefByRuntimeIdOperation
                                    .selectRuntimeServerContainerRefByRuntimeId(sqlConnection,
                                            shard.shard(),
                                            runtimeId));
                })
                .map(FindRuntimeServerContainerRefResponse::new);
    }
}
