package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.findMatchmakerMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchId.SelectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation;
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
    public Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            final FindMatchmakerMatchRuntimeRefRequest request) {
        log.debug("Find matchmaker match runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation
                                    .selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchId(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            matchId
                                    ));
                })
                .map(FindMatchmakerMatchRuntimeRefResponse::new);
    }
}
