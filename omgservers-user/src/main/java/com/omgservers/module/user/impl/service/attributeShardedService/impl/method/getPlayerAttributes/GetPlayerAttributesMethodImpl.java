package com.omgservers.module.user.impl.service.attributeShardedService.impl.method.getPlayerAttributes;

import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
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
    public Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request) {
        GetPlayerAttributesShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var playerId = request.getPlayerId();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerAttributesOperation
                            .selectPlayerAttributes(sqlConnection, shardModel.shard(), playerId));
                })
                .map(GetPlayerAttributesShardedResponse::new);
    }
}
