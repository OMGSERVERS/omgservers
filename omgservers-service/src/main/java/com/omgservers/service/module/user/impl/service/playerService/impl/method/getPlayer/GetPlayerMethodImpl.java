package com.omgservers.service.module.user.impl.service.playerService.impl.method.getPlayer;

import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.service.module.user.impl.operation.selectPlayer.SelectPlayerOperation;
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
        log.debug("Get player, request={}", request);

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
