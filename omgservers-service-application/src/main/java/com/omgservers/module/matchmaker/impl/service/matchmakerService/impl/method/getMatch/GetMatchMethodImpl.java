package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatch;

import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.module.matchmaker.impl.operation.selectMatch.SelectMatchOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchMethodImpl implements GetMatchMethod {

    final SelectMatchOperation selectMatchOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchResponse> getMatch(GetMatchRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var id = request.getId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectMatchOperation
                            .selectMatch(sqlConnection, shard.shard(), matchmakerId, id, deleted));
                })
                .map(GetMatchResponse::new);
    }
}
