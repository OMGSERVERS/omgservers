package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchRuntimeRef;

import com.omgservers.model.dto.matchmaker.GetMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchRuntimeRefResponse;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchRuntimeRef.SelectMatchRuntimeRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchRuntimeRefMethodImpl implements GetMatchRuntimeRefMethod {

    final SelectMatchRuntimeRefOperation selectMatchRuntimeRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchRuntimeRefResponse> getMatchRuntimeRef(final GetMatchRuntimeRefRequest request) {
        log.debug("Get match runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var matchId = request.getMatchId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchRuntimeRefOperation
                            .selectMatchRuntimeRef(sqlConnection,
                                    shard.shard(),
                                    matchmakerId,
                                    matchId,
                                    id));
                })
                .map(GetMatchRuntimeRefResponse::new);
    }
}
