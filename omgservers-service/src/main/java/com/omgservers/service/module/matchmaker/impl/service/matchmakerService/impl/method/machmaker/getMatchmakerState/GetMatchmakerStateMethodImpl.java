package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmakerState;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.selectActiveMatchmakerMatchesByMatchmakerId.SelectActiveMatchmakerMatchesByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerCommand.selectActiveMatchmakerCommandsByMatchmakerId.SelectActiveMatchmakerCommandsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClientsByMatchmakerId.SelectMatchmakerMatchClientsByMatchmakerIdOperation;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.selectActiveMatchmakerRequestsByMatchmakerId.SelectActiveMatchmakerRequestsByMatchmakerIdOperation;
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
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var shard = shardModel.shard();
                    return pgPool.withTransaction(sqlConnection ->
                            selectActiveMatchmakerCommandsByMatchmakerIdOperation
                                    .selectActiveMatchmakerCommandsByMatchmakerId(sqlConnection,
                                            shard,
                                            matchmakerId)
                                    .flatMap(commands -> selectActiveMatchmakerRequestsByMatchmakerIdOperation
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
                                                            .map(clients -> new MatchmakerStateDto(
                                                                    commands,
                                                                    requests,
                                                                    matches,
                                                                    clients)
                                                            )
                                                    )
                                            )
                                    )
                    );
                })
                .map(GetMatchmakerStateResponse::new);
    }
}
