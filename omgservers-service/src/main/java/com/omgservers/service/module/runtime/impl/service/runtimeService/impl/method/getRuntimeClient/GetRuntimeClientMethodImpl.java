package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.getRuntimeClient;

import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeClient.SelectRuntimeClientOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeClientMethodImpl implements GetRuntimeClientMethod {

    final SelectRuntimeClientOperation selectRuntimeClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeClientResponse> getRuntimeClient(final GetRuntimeClientRequest request) {
        log.debug("Get runtime client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeClientOperation
                            .selectRuntimeClient(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    id));
                })
                .map(GetRuntimeClientResponse::new);
    }
}
