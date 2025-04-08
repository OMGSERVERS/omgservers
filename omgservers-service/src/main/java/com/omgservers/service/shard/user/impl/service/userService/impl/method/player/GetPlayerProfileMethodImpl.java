package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.user.GetPlayerProfileRequest;
import com.omgservers.schema.module.user.GetPlayerProfileResponse;
import com.omgservers.service.shard.user.impl.operation.userPlayer.SelectPlayerProfileOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPlayerProfileMethodImpl implements GetPlayerProfileMethod {

    final SelectPlayerProfileOperation selectPlayerProfileOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerProfileResponse> getPlayerProfile(final ShardModel shardModel,
                                                          final GetPlayerProfileRequest request) {
        log.trace("{}", request);

        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        return pgPool.withTransaction(sqlConnection -> selectPlayerProfileOperation
                        .execute(sqlConnection, shardModel.shard(), userId, playerId))
                .map(GetPlayerProfileResponse::new);
    }
}
