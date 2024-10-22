package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.getMatchmakerMatch;

import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.selectMatchmakerMatch.SelectMatchmakerMatchOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMatchMethodImpl implements GetMatchmakerMatchMethod {

    final SelectMatchmakerMatchOperation selectMatchmakerMatchOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(final GetMatchmakerMatchRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerMatchOperation
                            .selectMatchmakerMatch(sqlConnection, shard.shard(), matchmakerId, id));
                })
                .map(GetMatchmakerMatchResponse::new);
    }
}
