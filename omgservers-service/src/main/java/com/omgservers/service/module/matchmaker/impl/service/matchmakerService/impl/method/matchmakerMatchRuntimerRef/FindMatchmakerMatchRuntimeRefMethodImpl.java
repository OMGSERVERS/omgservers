package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.SelectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindMatchmakerMatchRuntimeRefMethodImpl implements FindMatchmakerMatchRuntimeRefMethod {

    final SelectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation
            selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> execute(
            final FindMatchmakerMatchRuntimeRefRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            matchId
                                    ));
                })
                .map(FindMatchmakerMatchRuntimeRefResponse::new);
    }
}
