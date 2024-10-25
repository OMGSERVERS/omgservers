package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerCommand.SelectActiveMatchmakerCommandsByMatchmakerIdOperation;
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

    final SelectActiveMatchmakerCommandsByMatchmakerIdOperation selectActiveMatchmakerCommandsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerCommandsResponse> execute(final ViewMatchmakerCommandsRequest request) {
        log.debug("Requested, {}", request);

        final var matchmakerId = request.getMatchmakerId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> pgPool.withTransaction(
                        sqlConnection -> selectActiveMatchmakerCommandsByMatchmakerIdOperation
                                .execute(sqlConnection,
                                        shard.shard(),
                                        matchmakerId
                                )))
                .map(ViewMatchmakerCommandsResponse::new);

    }
}
