package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment.SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    public Uni<ViewMatchmakerAssignmentsResponse> execute(ViewMatchmakerAssignmentsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveMatchmakerAssignmentsByMatchmakerIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            matchmakerId));
                })
                .map(ViewMatchmakerAssignmentsResponse::new);

    }
}
