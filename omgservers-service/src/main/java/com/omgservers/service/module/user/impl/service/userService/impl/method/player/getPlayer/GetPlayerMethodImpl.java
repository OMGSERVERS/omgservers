package com.omgservers.service.module.user.impl.service.userService.impl.method.player.getPlayer;

import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayer.SelectPlayerOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPlayerMethodImpl implements GetPlayerMethod {

    final CheckShardOperation checkShardOperation;
    final SelectPlayerOperation selectPlayerOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerResponse> getPlayer(final GetPlayerRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerOperation
                            .selectPlayer(sqlConnection, shard.shard(), userId, id));
                })
                .map(GetPlayerResponse::new);
    }
}
