package com.omgservers.service.module.user.impl.service.userService.impl.method.player.getPlayerProfile;

import com.omgservers.schema.module.user.GetPlayerProfileRequest;
import com.omgservers.schema.module.user.GetPlayerProfileResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayerProfile.SelectPlayerProfileOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerProfileResponse> getPlayerProfile(final GetPlayerProfileRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerProfileOperation
                            .selectPlayerProfile(sqlConnection, shard.shard(), userId, playerId));
                })
                .map(GetPlayerProfileResponse::new);
    }
}
