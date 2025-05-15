package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.SelectMatchmakerMatchAssignmentByMatchmakerIdAndClientIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<FindMatchmakerMatchAssignmentResponse> execute(final ShardModel shardModel,
                                                              final FindMatchmakerMatchAssignmentRequest request) {
        log.debug("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var clientId = request.getClientId();
        return pgPool.withTransaction(
                        sqlConnection -> selectMatchmakerMatchAssignmentByMatchmakerIdAndClientIdOperation
                                .execute(sqlConnection,
                                        shardModel.slot(),
                                        matchmakerId,
                                        clientId))
                .map(FindMatchmakerMatchAssignmentResponse::new);
    }
}
