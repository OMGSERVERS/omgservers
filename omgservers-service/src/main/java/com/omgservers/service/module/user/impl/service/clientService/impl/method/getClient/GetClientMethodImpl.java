package com.omgservers.service.module.user.impl.service.clientService.impl.method.getClient;

import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.service.exception.ServerSideGoneException;
import com.omgservers.service.module.user.impl.operation.selectClient.SelectClientOperation;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientMethodImpl implements GetClientMethod {

    final CalculateShardOperation calculateShardOperation;
    final SelectClientOperation selectClientOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    if (shardModel.foreign()) {
                        throw new ServerSideGoneException("wrong shard server, shard=" + shardModel.shard());
                    }

                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection -> selectClientOperation
                            .selectClient(sqlConnection, shard, userId, clientId));
                })
                .map(GetClientResponse::new);
    }
}
