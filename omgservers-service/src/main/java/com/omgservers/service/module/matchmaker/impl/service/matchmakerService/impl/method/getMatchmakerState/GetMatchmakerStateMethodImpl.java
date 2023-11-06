package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerState;

import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchClientsByMatchmakerId.SelectMatchClientsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchesByMatchmakerId.SelectMatchesByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerId.SelectMatchmakerCommandsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.selectRequestsByMatchmakerId.SelectRequestsByMatchmakerIdOperation;
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

    final SelectMatchmakerCommandsByMatchmakerIdOperation selectMatchmakerCommandsByMatchmakerIdOperation;
    final SelectMatchClientsByMatchmakerIdOperation selectMatchClientsByMatchmakerIdOperation;
    final SelectRequestsByMatchmakerIdOperation selectRequestsByMatchmakerIdOperation;
    final SelectMatchesByMatchmakerIdOperation selectMatchesByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(final GetMatchmakerStateRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection ->
                            selectMatchmakerCommandsByMatchmakerIdOperation
                                    .selectMatchmakerCommandsByMatchmakerIdAndMatchId(sqlConnection,
                                            shard,
                                            matchmakerId)
                                    .flatMap(matchmakerCommands -> selectRequestsByMatchmakerIdOperation
                                            .selectRequestsByMatchmakerId(sqlConnection,
                                                    shard,
                                                    matchmakerId)
                                            .flatMap(requests -> selectMatchesByMatchmakerIdOperation
                                                    .selectMatchesByMatchmakerId(
                                                            sqlConnection,
                                                            shard,
                                                            matchmakerId)
                                                    .flatMap(matches -> selectMatchClientsByMatchmakerIdOperation
                                                            .selectMatchClientsByMatchmakerId(
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
