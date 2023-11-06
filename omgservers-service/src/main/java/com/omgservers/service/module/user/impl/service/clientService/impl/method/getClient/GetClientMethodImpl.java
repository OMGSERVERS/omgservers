package com.omgservers.service.module.user.impl.service.clientService.impl.method.getClient;

import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.service.module.user.impl.operation.selectClient.SelectClientOperation;
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
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var deleted = request.getDeleted();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection -> selectClientOperation
                            .selectClient(sqlConnection, shard, userId, clientId, deleted));
                })
                .map(GetClientResponse::new);
    }
}
