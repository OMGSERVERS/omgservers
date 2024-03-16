package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerMatchCommands;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerMatchCommandsByMatchId.SelectActiveMatchmakerMatchCommandsByMatchIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerMatchCommandsMethodImpl implements ViewMatchmakerMatchCommandsMethod {

    final SelectActiveMatchmakerMatchCommandsByMatchIdOperation selectActiveMatchmakerMatchCommandsByMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerMatchCommandsResponse> viewMatchmakerMatchCommands(
            final ViewMatchmakerMatchCommandsRequest request) {
        log.debug("View matchmaker match commands, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> pgPool.withTransaction(
                        sqlConnection -> selectActiveMatchmakerMatchCommandsByMatchIdOperation
                                .selectActiveMatchmakerMatchCommandsByMatchId(sqlConnection,
                                        shard.shard(),
                                        matchmakerId,
                                        matchId
                                )))
                .map(ViewMatchmakerMatchCommandsResponse::new);

    }
}
