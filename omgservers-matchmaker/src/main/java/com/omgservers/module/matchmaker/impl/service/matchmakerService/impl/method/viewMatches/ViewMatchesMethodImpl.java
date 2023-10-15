package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatches;

import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.module.matchmaker.impl.operation.selectMatchesByMatchmakerId.SelectMatchesByMatchmakerIdOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchesMethodImpl implements ViewMatchesMethod {

    final SelectMatchesByMatchmakerIdOperation selectMatchesByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectMatchesByMatchmakerIdOperation
                            .selectMatchesByMatchmakerId(sqlConnection, shard.shard(), matchmakerId, deleted));
                })
                .map(ViewMatchesResponse::new);

    }
}
