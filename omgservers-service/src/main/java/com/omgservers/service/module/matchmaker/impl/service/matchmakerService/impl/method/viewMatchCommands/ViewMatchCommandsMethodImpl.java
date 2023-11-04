package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchCommands;

import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchCommandsByMatchmakerIdAndMatchId.SelectMatchCommandsByMatchmakerIdAndMatchIdOperation;
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

    final SelectMatchCommandsByMatchmakerIdAndMatchIdOperation selectMatchCommandsByMatchmakerIdAndMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchCommandsResponse> viewMatchCommands(ViewMatchCommandsRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchCommandsByMatchmakerIdAndMatchIdOperation
                                    .selectMatchCommandsByMatchmakerIdAndMatchId(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            matchId
                                    ));
                })
                .map(ViewMatchCommandsResponse::new);

    }
}
