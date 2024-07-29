package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.viewMatchmakerMatchClients;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectActiveMatchmakerMatchClientsByMatchId.SelectActiveMatchmakerMatchClientsByMatchIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerMatchClientsMethodImpl implements ViewMatchmakerMatchClientsMethod {

    final SelectActiveMatchmakerMatchClientsByMatchIdOperation selectActiveMatchmakerMatchClientsByMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(
            final ViewMatchmakerMatchClientsRequest request) {
        log.debug("View matchmaker match clients, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerMatchClientsByMatchIdOperation
                            .selectActiveMatchmakerMatchClientsByMatchId(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    matchId));
                })
                .map(ViewMatchmakerMatchClientsResponse::new);

    }
}
