package com.omgservers.module.user.impl.service.attributeService.impl.method.getAttribute;

import com.omgservers.dto.user.GetAttributeRequest;
import com.omgservers.dto.user.GetAttributeResponse;
import com.omgservers.module.user.impl.operation.selectAttribute.SelectAttributeOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetAttributeMethodImpl implements GetAttributeMethod {

    final SelectAttributeOperation selectAttributeOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetAttributeResponse> getAttribute(final GetAttributeRequest request) {
        GetAttributeRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    final var name = request.getName();
                    return pgPool.withTransaction(sqlConnection -> selectAttributeOperation
                            .selectAttribute(sqlConnection, shard.shard(), userId, playerId, name));
                })
                .map(GetAttributeResponse::new);
    }
}
