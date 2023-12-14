package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.countRuntimeClients;

import com.omgservers.model.dto.runtime.CountRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeClientsResponse;
import com.omgservers.service.module.runtime.impl.operation.countRuntimeClients.CountRuntimeClientsOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CountRuntimeClientsMethodImpl implements CountRuntimeClientsMethod {

    final CountRuntimeClientsOperation countRuntimeClientsOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<CountRuntimeClientsResponse> countRuntimeClients(final CountRuntimeClientsRequest request) {
        log.debug("Count runtime clients, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();

                    return pgPool.withTransaction(sqlConnection -> countRuntimeClientsOperation
                            .countRuntimeClients(sqlConnection,
                                    shard.shard(),
                                    runtimeId)
                    );
                })
                .map(CountRuntimeClientsResponse::new);
    }
}
