package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerAssignment;

import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerAssignment.SelectMatchmakerAssignmentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerAssignmentMethodImpl implements GetMatchmakerAssignmentMethod {

    final SelectMatchmakerAssignmentOperation selectMatchmakerAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(final GetMatchmakerAssignmentRequest request) {
        log.debug("Get matchmaker assignment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerAssignmentOperation
                            .selectMatchmakerAssignment(sqlConnection, shard.shard(), matchmakerId, id));
                })
                .map(GetMatchmakerAssignmentResponse::new);
    }
}
