package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.SelectMatchmakerOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand.SelectActiveMatchmakerCommandsByMatchmakerIdOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.SelectActiveMatchmakerMatchAssignmentsByMatchmakerIdOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.SelectActiveMatchmakerRequestsByMatchmakerIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerStateMethodImpl implements GetMatchmakerStateMethod {

    final SelectActiveMatchmakerMatchAssignmentsByMatchmakerIdOperation
            selectActiveMatchmakerMatchAssignmentsByMatchmakerIdOperation;
    final SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperation
            selectActiveMatchmakerMatchResourcesByMatchmakerIdOperation;
    final SelectActiveMatchmakerCommandsByMatchmakerIdOperation
            selectActiveMatchmakerCommandsByMatchmakerIdOperation;
    final SelectActiveMatchmakerRequestsByMatchmakerIdOperation
            selectActiveMatchmakerRequestsByMatchmakerIdOperation;

    final SelectMatchmakerOperation selectMatchmakerOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerStateResponse> execute(final ShardModel shardModel,
                                                   final GetMatchmakerStateRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var shard = shardModel.shard();
        return pgPool.withTransaction(sqlConnection -> {
                    final var matchmakerState = new MatchmakerStateDto();
                    return selectMatchmakerOperation.execute(sqlConnection, shard, matchmakerId)
                            .invoke(matchmakerState::setMatchmaker)
                            .flatMap(matchmaker ->
                                    selectActiveMatchmakerCommandsByMatchmakerIdOperation
                                            .execute(sqlConnection, shard, matchmakerId))
                            .invoke(matchmakerState::setMatchmakerCommands)
                            .flatMap(matchmaker ->
                                    selectActiveMatchmakerRequestsByMatchmakerIdOperation
                                            .execute(sqlConnection, shard, matchmakerId))
                            .invoke(matchmakerState::setMatchmakerRequests)
                            .flatMap(matchmaker ->
                                    selectActiveMatchmakerMatchResourcesByMatchmakerIdOperation
                                            .execute(sqlConnection, shard, matchmakerId))
                            .invoke(matchmakerState::setMatchmakerMatchResources)
                            .flatMap(matchmakerMatches ->
                                    selectActiveMatchmakerMatchAssignmentsByMatchmakerIdOperation
                                            .execute(sqlConnection, shard, matchmakerId))
                            .invoke(matchmakerState::setMatchmakerMatchAssignments)
                            .replaceWith(matchmakerState);
                })
                .map(GetMatchmakerStateResponse::new);
    }
}
