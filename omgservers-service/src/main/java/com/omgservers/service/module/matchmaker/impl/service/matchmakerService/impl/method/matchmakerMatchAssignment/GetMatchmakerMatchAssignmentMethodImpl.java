package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment.SelectMatchmakerMatchAssignmentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerMatchAssignmentResponse> execute(
            final GetMatchmakerMatchAssignmentRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerMatchAssignmentOperation
                            .execute(sqlConnection, shard.shard(), matchmakerId, id));
                })
                .map(GetMatchmakerMatchAssignmentResponse::new);
    }
}
