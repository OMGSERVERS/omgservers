package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.viewMatchmakerAssignments;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.selectActiveMatchmakerAssignmentsByMatchmakerId.SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerAssignmentsMethodImpl implements ViewMatchmakerAssignmentsMethod {

    final SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation
            selectActiveMatchmakerAssignmentsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(ViewMatchmakerAssignmentsRequest request) {
        log.debug("View matchmaker assignments, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveMatchmakerAssignmentsByMatchmakerIdOperation
                                    .selectActiveMatchmakerAssignmentsByMatchmakerId(sqlConnection,
                                            shard.shard(),
                                            matchmakerId));
                })
                .map(ViewMatchmakerAssignmentsResponse::new);

    }
}
