package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchClient;

import com.omgservers.model.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchClientByMatchmakerIdAndClientId.SelectMatchClientByMatchmakerIdAndClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindMatchClientMethodImpl implements FindMatchClientMethod {

    final SelectMatchClientByMatchmakerIdAndClientIdOperation selectMatchClientByMatchmakerIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindMatchClientResponse> findMatchClient(final FindMatchClientRequest request) {
        log.debug("Find match client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchClientByMatchmakerIdAndClientIdOperation
                            .selectMatchClientByMatchmakerIdAndClientId(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    clientId));
                })
                .map(FindMatchClientResponse::new);
    }
}
