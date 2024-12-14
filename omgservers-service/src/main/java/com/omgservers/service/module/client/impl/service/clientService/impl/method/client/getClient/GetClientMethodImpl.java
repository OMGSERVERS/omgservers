package com.omgservers.service.module.client.impl.service.clientService.impl.method.client.getClient;

import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.service.module.client.impl.operation.client.selectClient.SelectClientOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientMethodImpl implements GetClientMethod {

    final SelectClientOperation selectClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectClientOperation
                            .selectClient(sqlConnection, shard.shard(), id));
                })
                .map(GetClientResponse::new);
    }
}
