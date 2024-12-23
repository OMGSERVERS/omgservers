package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment.SelectActiveMatchmakerMatchAssignmentsByMatchIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerMatchAssignmentsResponse> execute(
            final ViewMatchmakerMatchAssignmentsRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerMatchAssignmentsByMatchIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    matchId));
                })
                .map(ViewMatchmakerMatchAssignmentsResponse::new);

    }
}
