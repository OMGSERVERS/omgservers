package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.SelectActiveMatchmakerMatchAssignmentsByMatchIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerMatchAssignmentsMethodImpl implements ViewMatchmakerMatchAssignmentsMethod {

    final SelectActiveMatchmakerMatchAssignmentsByMatchIdOperation
            selectActiveMatchmakerMatchAssignmentsByMatchIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerMatchAssignmentsResponse> execute(final ShardModel shardModel,
                                                               final ViewMatchmakerMatchAssignmentsRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerMatchAssignmentsByMatchIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                matchmakerId,
                                matchId))
                .map(ViewMatchmakerMatchAssignmentsResponse::new);

    }
}
