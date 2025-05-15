package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.SelectMatchmakerMatchAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMatchAssignmentMethodImpl implements GetMatchmakerMatchAssignmentMethod {

    final SelectMatchmakerMatchAssignmentOperation selectMatchmakerMatchAssignmentOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerMatchAssignmentResponse> execute(final ShardModel shardModel,
                                                             final GetMatchmakerMatchAssignmentRequest request) {
        log.debug("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerMatchAssignmentOperation
                        .execute(sqlConnection, shardModel.slot(), matchmakerId, id))
                .map(GetMatchmakerMatchAssignmentResponse::new);
    }
}
