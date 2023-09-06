package com.omgservers.module.user.impl.service.playerService.impl.method.getPlayer;

import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.module.user.impl.operation.selectPlayer.SelectPlayerOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
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
    public Uni<GetPlayerShardedResponse> getPlayer(final GetPlayerShardedRequest request) {
        GetPlayerShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var user = request.getUserId();
                    final var stage = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerOperation
                            .selectPlayer(sqlConnection, shard.shard(), user, stage));
                })
                .map(GetPlayerShardedResponse::new);
    }
}
