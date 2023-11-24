package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchClients;

import com.omgservers.model.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchClientsByMatchId.SelectActiveMatchClientsByMatchIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchClientsMethodImpl implements ViewMatchClientsMethod {

    final SelectActiveMatchClientsByMatchIdOperation selectActiveMatchClientsByMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchClientsResponse> viewMatchClients(final ViewMatchClientsRequest request) {
        log.debug("View match clients, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchClientsByMatchIdOperation
                            .selectActiveMatchClientsByMatchId(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    matchId));
                })
                .map(ViewMatchClientsResponse::new);

    }
}
