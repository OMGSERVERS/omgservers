package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.FindPlayerRequest;
import com.omgservers.schema.shard.user.FindPlayerResponse;
import com.omgservers.service.shard.user.impl.operation.userPlayer.SelectPlayerByUserIdAndStageIdOperation;
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
    final PgPool pgPool;

    @Override
    public Uni<FindPlayerResponse> findPlayer(final ShardModel shardModel,
                                              final FindPlayerRequest request) {
        log.trace("{}", request);

        final var userId = request.getUserId();
        final var tenantStageId = request.getTenantStageId();
        return pgPool.withTransaction(sqlConnection -> selectPlayerByUserIdAndStageIdOperation
                        .execute(sqlConnection, shardModel.slot(), userId, tenantStageId))
                .map(FindPlayerResponse::new);
    }
}
