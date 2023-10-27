package com.omgservers.module.user.impl.service.playerService.impl.method.getPlayerObject;

import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.module.user.impl.operation.selectPlayerObject.SelectPlayerObjectOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPlayerObjectMethodImpl implements GetPlayerObjectMethod {

    final SelectPlayerObjectOperation selectPlayerObjectOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerObjectResponse> getPlayerObject(final GetPlayerObjectRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerObjectOperation
                            .selectPlayerObject(sqlConnection, shard.shard(), userId, playerId));
                })
                .map(GetPlayerObjectResponse::new);
    }
}
