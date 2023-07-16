package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod;

import com.omgservers.application.module.userModule.impl.operation.selectPlayerAttributesOperation.SelectPlayerAttributesOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetPlayerAttributesInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request) {
        GetPlayerAttributesInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var player = request.getPlayer();
                    return pgPool.withTransaction(sqlConnection -> selectPlayerAttributesOperation
                            .selectPlayerAttributes(sqlConnection, shardModel.shard(), player));
                })
                .map(GetPlayerAttributesInternalResponse::new);
    }
}
