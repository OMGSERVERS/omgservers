package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerCommands;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerId.SelectMatchmakerCommandsByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerCommandsMethodImpl implements ViewMatchmakerCommandsMethod {

    final SelectMatchmakerCommandsByMatchmakerIdOperation selectMatchmakerCommandsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(final ViewMatchmakerCommandsRequest request) {
        final var matchmakerId = request.getMatchmakerId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> pgPool.withTransaction(
                        sqlConnection -> selectMatchmakerCommandsByMatchmakerIdOperation
                                .selectMatchmakerCommandsByMatchmakerIdAndMatchId(sqlConnection,
                                        shard.shard(),
                                        matchmakerId
                                )))
                .map(ViewMatchmakerCommandsResponse::new);

    }
}