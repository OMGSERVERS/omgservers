package com.omgservers.module.user.impl.service.clientShardedService.impl.method.getClient;

import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.exception.ServerSideGoneException;
import com.omgservers.module.user.impl.operation.selectClient.SelectClientOperation;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
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
    public Uni<GetClientShardedResponse> getClient(final GetClientShardedRequest request) {
        GetClientShardedRequest.validate(request);

        return calculateShardOperation.calculateShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    if (shardModel.foreign()) {
                        throw new ServerSideGoneException("wrong shard server, shard=" + shardModel.shard());
                    }

                    final var client = request.getClientId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection -> selectClientOperation
                            .selectClient(sqlConnection, shard, client));
                })
                .map(GetClientShardedResponse::new);
    }
}
