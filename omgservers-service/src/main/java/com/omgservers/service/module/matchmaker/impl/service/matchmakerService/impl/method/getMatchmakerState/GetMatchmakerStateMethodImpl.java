package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerState;

import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerMatchesByMatchmakerId.SelectActiveMatchmakerMatchesByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerCommandsByMatchmakerId.SelectActiveMatchmakerCommandsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerMatchClientsByMatchmakerId.SelectMatchmakerMatchClientsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerRequestsByMatchmakerId.SelectActiveMatchmakerRequestsByMatchmakerIdOperation;
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
    final SelectMatchmakerMatchClientsByMatchmakerIdOperation selectMatchmakerMatchClientsByMatchmakerIdOperation;
    final SelectActiveMatchmakerRequestsByMatchmakerIdOperation selectActiveMatchmakerRequestsByMatchmakerIdOperation;
    final SelectActiveMatchmakerMatchesByMatchmakerIdOperation selectActiveMatchmakerMatchesByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(final GetMatchmakerStateRequest request) {
        log.debug("Get matchmaker state, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection ->
                            selectActiveMatchmakerCommandsByMatchmakerIdOperation
                                    .selectActiveMatchmakerCommandsByMatchmakerId(sqlConnection,
                                            shard,
                                            matchmakerId)
                                    .flatMap(matchmakerCommands -> selectActiveMatchmakerRequestsByMatchmakerIdOperation
                                            .selectActiveMatchmakerRequestsByMatchmakerId(sqlConnection,
                                                    shard,
                                                    matchmakerId)
                                            .flatMap(requests -> selectActiveMatchmakerMatchesByMatchmakerIdOperation
                                                    .selectActiveMatchmakerMatchesByMatchmakerId(
                                                            sqlConnection,
                                                            shard,
                                                            matchmakerId)
                                                    .flatMap(matches -> selectMatchmakerMatchClientsByMatchmakerIdOperation
                                                            .selectMatchmakerMatchClientsByMatchmakerId(
                                                                    sqlConnection,
                                                                    shard,
                                                                    matchmakerId)
                                                            .map(matchClients -> new MatchmakerState(
                                                                    matchmakerCommands,
                                                                    requests,
                                                                    matches,
                                                                    matchClients)
                                                            )
                                                    )
                                            )
                                    )
                    );
                })
                .map(GetMatchmakerStateResponse::new);
    }
}
