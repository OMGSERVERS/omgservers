package com.omgservers.module.user.impl.service.playerService.impl.method.findPlayer;

import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.module.user.impl.operation.selectPlayerByUserIdAndStageId.SelectPlayerByUserIdAndStageIdOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindPlayerMethodImpl implements FindPlayerMethod {

    final SelectPlayerByUserIdAndStageIdOperation selectPlayerByUserIdAndStageIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindPlayerResponse> findPlayer(final FindPlayerRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerByUserIdAndStageIdOperation
                            .selectPlayerByUserIdAndStageId(sqlConnection, shard.shard(), userId, stageId));
                })
                .map(FindPlayerResponse::new);
    }
}
