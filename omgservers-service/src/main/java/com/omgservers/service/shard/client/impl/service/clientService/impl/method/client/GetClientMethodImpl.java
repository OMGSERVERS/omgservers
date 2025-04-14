package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.service.shard.client.impl.operation.client.SelectClientOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetClientResponse> execute(final ShardModel shardModel,
                                          final GetClientRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectClientOperation
                        .selectClient(sqlConnection, shardModel.shard(), id))
                .map(GetClientResponse::new);
    }
}
