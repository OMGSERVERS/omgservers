package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.SelectMatchmakerMatchAssignmentByMatchmakerIdAndClientIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindMatchmakerMatchAssignmentMethodImpl implements FindMatchmakerMatchAssignmentMethod {

    final SelectMatchmakerMatchAssignmentByMatchmakerIdAndClientIdOperation
            selectMatchmakerMatchAssignmentByMatchmakerIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindMatchmakerMatchAssignmentResponse> execute(
            final FindMatchmakerMatchAssignmentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchmakerMatchAssignmentByMatchmakerIdAndClientIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            clientId));
                })
                .map(FindMatchmakerMatchAssignmentResponse::new);
    }
}
