package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.getClientMatchmakerRef;

import com.omgservers.schema.module.client.GetClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.GetClientMatchmakerRefResponse;
import com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.selectClientMatchmakerRef.SelectClientMatchmakerRefOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientMatchmakerRefMethodImpl implements GetClientMatchmakerRefMethod {

    final SelectClientMatchmakerRefOperation selectClientMatchmakerRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(final GetClientMatchmakerRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectClientMatchmakerRefOperation
                            .selectClientMatchmakerRef(sqlConnection, shard.shard(), clientId, id));
                })
                .map(GetClientMatchmakerRefResponse::new);
    }
}
