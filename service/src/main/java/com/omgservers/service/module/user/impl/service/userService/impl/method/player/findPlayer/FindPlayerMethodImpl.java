package com.omgservers.service.module.user.impl.service.userService.impl.method.player.findPlayer;

import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayerByUserIdAndStageId.SelectPlayerByUserIdAndStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Find player, request={}", request);

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
