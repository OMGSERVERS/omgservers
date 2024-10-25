package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.SelectMatchmakerOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerCommand.SelectActiveMatchmakerCommandsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.SelectActiveMatchmakerMatchesByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment.SelectMatchmakerMatchAssignmentsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.SelectActiveMatchmakerRequestsByMatchmakerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerStateMethodImpl implements GetMatchmakerStateMethod {

    final SelectActiveMatchmakerCommandsByMatchmakerIdOperation selectActiveMatchmakerCommandsByMatchmakerIdOperation;
    final SelectActiveMatchmakerRequestsByMatchmakerIdOperation selectActiveMatchmakerRequestsByMatchmakerIdOperation;
    final SelectActiveMatchmakerMatchesByMatchmakerIdOperation selectActiveMatchmakerMatchesByMatchmakerIdOperation;
    final SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation
            selectActiveMatchmakerAssignmentsByMatchmakerIdOperation;
    final SelectMatchmakerMatchAssignmentsByMatchmakerIdOperation
            selectMatchmakerMatchAssignmentsByMatchmakerIdOperation;
    final SelectMatchmakerOperation selectMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerStateResponse> execute(final GetMatchmakerStateRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                            .execute(sqlConnection,
                                    shard,
                                    matchmakerId)
                            .flatMap(matchmaker -> selectActiveMatchmakerAssignmentsByMatchmakerIdOperation
                                    .execute(sqlConnection,
                                            shard,
                                            matchmakerId)
                                    .flatMap(matchmakerAssignments ->
                                            selectActiveMatchmakerCommandsByMatchmakerIdOperation
                                                    .execute(sqlConnection,
                                                            shard,
                                                            matchmakerId)
                                                    .flatMap(matchmakerCommands ->
                                                            selectActiveMatchmakerRequestsByMatchmakerIdOperation
                                                                    .execute(sqlConnection,
                                                                            shard,
                                                                            matchmakerId)
                                                                    .flatMap(matchmakerRequests ->
                                                                            selectActiveMatchmakerMatchesByMatchmakerIdOperation
                                                                                    .execute(sqlConnection,
                                                                                            shard,
                                                                                            matchmakerId)
                                                                                    .flatMap(
                                                                                            matchmakerMatches ->
                                                                                                    selectMatchmakerMatchAssignmentsByMatchmakerIdOperation
                                                                                                            .execute(
                                                                                                                    sqlConnection,
                                                                                                                    shard,
                                                                                                                    matchmakerId)
                                                                                                            .map(matchmakerMatchAssignments ->
                                                                                                                    new MatchmakerStateDto(
                                                                                                                            matchmaker,
                                                                                                                            matchmakerAssignments,
                                                                                                                            matchmakerCommands,
                                                                                                                            matchmakerRequests,
                                                                                                                            matchmakerMatches,
                                                                                                                            matchmakerMatchAssignments)
                                                                                                            )
                                                                                    )
                                                                    )
                                                    )
                                    )
                            )
                    );
                })
                .map(GetMatchmakerStateResponse::new);
    }
}