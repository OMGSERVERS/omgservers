package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerCommands;

import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerIdAndStatus.SelectMatchmakerCommandsByMatchmakerIdAndStatusOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerCommandsMethodImpl implements ViewMatchmakerCommandsMethod {

    final SelectMatchmakerCommandsByMatchmakerIdAndStatusOperation
            selectMatchmakerCommandsByMatchmakerIdAndStatusOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(ViewMatchmakerCommandsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var status = request.getStatus();
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchmakerCommandsByMatchmakerIdAndStatusOperation
                                    .selectMatchmakerCommandsByMatchmakerIdAndStatus(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            status));
                })
                .map(ViewMatchmakerCommandsResponse::new);

    }
}
