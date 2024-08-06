package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.getMatchmakerMatchRuntimeRef;

import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.selectMatchmakerMatchRuntimeRef.SelectMatchmakerMatchRuntimeRefOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMatchRuntimeRefMethodImpl implements GetMatchmakerMatchRuntimeRefMethod {

    final SelectMatchmakerMatchRuntimeRefOperation selectMatchmakerMatchRuntimeRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(
            final GetMatchmakerMatchRuntimeRefRequest request) {
        log.debug("Get matchmaker match runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerMatchRuntimeRefOperation
                            .selectMatchmakerMatchRuntimeRef(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    matchId,
                                    id));
                })
                .map(GetMatchmakerMatchRuntimeRefResponse::new);
    }
}
