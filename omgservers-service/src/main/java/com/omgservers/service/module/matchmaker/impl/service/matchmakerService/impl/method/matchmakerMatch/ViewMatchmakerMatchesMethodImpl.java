package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.SelectActiveMatchmakerMatchesByMatchmakerIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerMatchesMethodImpl implements ViewMatchmakerMatchesMethod {

    final SelectActiveMatchmakerMatchesByMatchmakerIdOperation selectActiveMatchmakerMatchesByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerMatchesResponse> execute(ViewMatchmakerMatchesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerMatchesByMatchmakerIdOperation
                            .execute(sqlConnection, shard.shard(), matchmakerId));
                })
                .map(ViewMatchmakerMatchesResponse::new);

    }
}
