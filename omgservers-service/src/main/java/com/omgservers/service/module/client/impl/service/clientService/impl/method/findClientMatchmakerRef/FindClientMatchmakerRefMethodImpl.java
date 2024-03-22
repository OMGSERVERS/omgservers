package com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientMatchmakerRef;

import com.omgservers.model.dto.client.FindClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.FindClientMatchmakerRefResponse;
import com.omgservers.service.module.client.impl.operation.selectClientMatchmakerRefByClientIdAndRuntimeId.SelectClientMatchmakerRefByClientIdAndMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindClientMatchmakerRefMethodImpl implements FindClientMatchmakerRefMethod {

    final SelectClientMatchmakerRefByClientIdAndMatchmakerIdOperation
            selectClientMatchmakerRefByClientIdAndMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(final FindClientMatchmakerRefRequest request) {
        log.debug("Find client matchmaker ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectClientMatchmakerRefByClientIdAndMatchmakerIdOperation
                                    .selectClientMatchmakerRefByClientIdAndMatchmakerId(sqlConnection,
                                            shard.shard(),
                                            clientId,
                                            matchmakerId));
                })
                .map(FindClientMatchmakerRefResponse::new);
    }
}
