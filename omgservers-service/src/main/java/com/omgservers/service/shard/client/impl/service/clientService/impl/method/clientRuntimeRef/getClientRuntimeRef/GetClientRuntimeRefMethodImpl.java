package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef.getClientRuntimeRef;

import com.omgservers.schema.module.client.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.GetClientRuntimeRefResponse;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.selectClientRuntimeRef.SelectClientRuntimeRefOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientRuntimeRefMethodImpl implements GetClientRuntimeRefMethod {

    final SelectClientRuntimeRefOperation selectClientRuntimeRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(final GetClientRuntimeRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectClientRuntimeRefOperation
                            .selectClientRuntimeRef(sqlConnection, shard.shard(), clientId, id));
                })
                .map(GetClientRuntimeRefResponse::new);
    }
}
