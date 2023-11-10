package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatches;

import com.omgservers.model.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchesByMatchmakerId.SelectActiveMatchesByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchesMethodImpl implements ViewMatchesMethod {

    final SelectActiveMatchesByMatchmakerIdOperation selectActiveMatchesByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchesByMatchmakerIdOperation
                            .selectActiveMatchesByMatchmakerId(sqlConnection, shard.shard(), matchmakerId));
                })
                .map(ViewMatchesResponse::new);

    }
}
