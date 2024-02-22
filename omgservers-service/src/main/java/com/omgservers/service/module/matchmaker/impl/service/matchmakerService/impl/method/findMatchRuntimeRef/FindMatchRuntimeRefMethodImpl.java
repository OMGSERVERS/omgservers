package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.FindMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchRuntimeRefByMatchmakerIdAndMatchId.SelectMatchRuntimeRefByMatchmakerIdAndMatchIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindMatchRuntimeRefMethodImpl implements FindMatchRuntimeRefMethod {

    final SelectMatchRuntimeRefByMatchmakerIdAndMatchIdOperation
            selectMatchRuntimeRefByMatchmakerIdAndMatchIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindMatchRuntimeRefResponse> findMatchRuntimeRef(final FindMatchRuntimeRefRequest request) {
        log.debug("Find match runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchRuntimeRefByMatchmakerIdAndMatchIdOperation
                                    .selectMatchRuntimeRefByMatchmakerIdAndMatchId(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            matchId
                                    ));
                })
                .map(FindMatchRuntimeRefResponse::new);
    }
}
