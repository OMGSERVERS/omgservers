package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.getRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.selectRuntimeServerContainerRef.SelectRuntimeServerContainerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeServerContainerRefMethodImpl implements GetRuntimeServerContainerRefMethod {

    final SelectRuntimeServerContainerRefOperation selectRuntimeServerContainerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeServerContainerRefResponse> getRuntimeServerContainerRef(
            final GetRuntimeServerContainerRefRequest request) {
        log.debug("Get runtime server container ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeServerContainerRefOperation
                            .selectRuntimeServerContainerRef(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    id));
                })
                .map(GetRuntimeServerContainerRefResponse::new);
    }
}
