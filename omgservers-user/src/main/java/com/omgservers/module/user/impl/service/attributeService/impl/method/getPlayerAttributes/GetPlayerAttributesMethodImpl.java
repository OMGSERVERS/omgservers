package com.omgservers.module.user.impl.service.attributeService.impl.method.getPlayerAttributes;

import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.module.user.impl.operation.selectPlayerAttributes.SelectPlayerAttributesOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetPlayerAttributesMethodImpl implements GetPlayerAttributesMethod {

    final SelectPlayerAttributesOperation selectPlayerAttributesOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        GetPlayerAttributesRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerAttributesOperation
                            .selectPlayerAttributes(sqlConnection, shardModel.shard(), userId, playerId));
                })
                .map(GetPlayerAttributesResponse::new);
    }
}
