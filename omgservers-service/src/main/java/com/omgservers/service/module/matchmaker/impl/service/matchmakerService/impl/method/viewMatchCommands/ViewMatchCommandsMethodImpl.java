package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchCommands;

import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchCommandsByMatchId.SelectActiveMatchCommandsByMatchIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchCommandsMethodImpl implements ViewMatchCommandsMethod {

    final SelectActiveMatchCommandsByMatchIdOperation selectActiveMatchCommandsByMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchCommandsResponse> viewMatchCommands(final ViewMatchCommandsRequest request) {
        log.debug("View match commands, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> pgPool.withTransaction(
                        sqlConnection -> selectActiveMatchCommandsByMatchIdOperation
                                .selectActiveMatchCommandsByMatchId(sqlConnection,
                                        shard.shard(),
                                        matchmakerId,
                                        matchId
                                )))
                .map(ViewMatchCommandsResponse::new);

    }
}
