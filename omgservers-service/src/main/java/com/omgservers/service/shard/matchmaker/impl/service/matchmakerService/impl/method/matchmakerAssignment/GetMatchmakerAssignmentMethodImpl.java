package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment.SelectMatchmakerAssignmentOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    public Uni<GetMatchmakerAssignmentResponse> execute(final GetMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerAssignmentOperation
                            .execute(sqlConnection, shard.shard(), matchmakerId, id));
                })
                .map(GetMatchmakerAssignmentResponse::new);
    }
}
