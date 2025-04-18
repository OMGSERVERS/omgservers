package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand.SelectActiveMatchmakerCommandsByMatchmakerIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerCommandsMethodImpl implements ViewMatchmakerCommandsMethod {

    final SelectActiveMatchmakerCommandsByMatchmakerIdOperation selectActiveMatchmakerCommandsByMatchmakerIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerCommandsResponse> execute(final ShardModel shardModel,
                                                       final ViewMatchmakerCommandsRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();

        return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerCommandsByMatchmakerIdOperation
                        .execute(sqlConnection, shardModel.slot(), matchmakerId))
                .map(ViewMatchmakerCommandsResponse::new);

    }
}
