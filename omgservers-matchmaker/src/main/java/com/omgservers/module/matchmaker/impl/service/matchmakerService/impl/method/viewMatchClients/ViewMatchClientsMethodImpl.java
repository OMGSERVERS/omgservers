package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchClients;

import com.omgservers.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.module.matchmaker.impl.operation.selectMatchClientsByMatchmakerId.SelectMatchClientsByMatchmakerIdOperation;
import com.omgservers.module.matchmaker.impl.operation.selectMatchClientsByMatchmakerIdAndMatchId.SelectMatchClientsByMatchmakerIdAndMatchIdOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchClientsMethodImpl implements ViewMatchClientsMethod {

    final SelectMatchClientsByMatchmakerIdAndMatchIdOperation selectMatchClientsByMatchmakerIdAndMatchIdOperation;
    final SelectMatchClientsByMatchmakerIdOperation selectMatchClientsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchClientsByMatchmakerIdAndMatchIdOperation
                            .selectMatchClientsByMatchmakerIdAndMatchId(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    matchId));
                })
                .map(ViewMatchClientsResponse::new);

    }
}
