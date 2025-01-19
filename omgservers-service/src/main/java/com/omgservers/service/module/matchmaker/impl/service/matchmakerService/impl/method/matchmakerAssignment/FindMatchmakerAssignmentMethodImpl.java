package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.SelectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindMatchmakerAssignmentMethodImpl implements FindMatchmakerAssignmentMethod {

    final SelectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation
            selectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindMatchmakerAssignmentResponse> execute(
            final FindMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            clientId));
                })
                .map(FindMatchmakerAssignmentResponse::new);
    }
}
