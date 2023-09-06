package com.omgservers.module.user.impl.service.attributeService.impl.method.getAttribute;

import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
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
    public Uni<GetAttributeShardedResponse> getAttribute(final GetAttributeShardedRequest request) {
        GetAttributeShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var player = request.getPlayerId();
                    final var name = request.getName();
                    return pgPool.withTransaction(sqlConnection -> selectAttributeOperation
                            .selectAttribute(sqlConnection, shard.shard(), player, name));
                })
                .map(GetAttributeShardedResponse::new);
    }
}
