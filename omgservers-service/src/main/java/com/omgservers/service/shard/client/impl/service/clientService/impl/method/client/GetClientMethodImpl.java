package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.module.client.client.GetClientRequest;
import com.omgservers.schema.module.client.client.GetClientResponse;
import com.omgservers.service.shard.client.impl.operation.client.SelectClientOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    public Uni<GetClientResponse> execute(final GetClientRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectClientOperation
                            .selectClient(sqlConnection, shard.shard(), id));
                })
                .map(GetClientResponse::new);
    }
}
