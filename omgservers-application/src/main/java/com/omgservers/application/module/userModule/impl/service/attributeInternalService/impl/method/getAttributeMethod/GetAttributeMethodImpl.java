package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod;

import com.omgservers.application.module.userModule.impl.operation.selectAttributeOperation.SelectAttributeOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.userModule.GetAttributeShardRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
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
    public Uni<GetAttributeInternalResponse> getAttribute(final GetAttributeShardRequest request) {
        GetAttributeShardRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var player = request.getPlayerId();
                    final var name = request.getName();
                    return pgPool.withTransaction(sqlConnection -> selectAttributeOperation
                            .selectAttribute(sqlConnection, shard.shard(), player, name));
                })
                .map(GetAttributeInternalResponse::new);
    }
}
