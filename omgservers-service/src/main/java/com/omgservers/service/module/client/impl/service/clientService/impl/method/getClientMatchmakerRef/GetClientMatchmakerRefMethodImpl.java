package com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientMatchmakerRef;

import com.omgservers.model.dto.client.GetClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.GetClientMatchmakerRefResponse;
import com.omgservers.service.module.client.impl.operation.selectClientMatchmakerRef.SelectClientMatchmakerRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Get client matchmaker ref, request={}", request);

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
