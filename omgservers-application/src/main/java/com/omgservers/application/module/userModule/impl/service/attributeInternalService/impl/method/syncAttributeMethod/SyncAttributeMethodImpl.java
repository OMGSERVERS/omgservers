package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod;

import com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation.UpsertAttributeOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncAttributeMethodImpl implements SyncAttributeMethod {

    final CheckShardOperation checkShardOperation;
    final UpsertAttributeOperation upsertAttributeOperation;
    final PgPool pgPool;

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request) {
        SyncAttributeInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var attribute = request.getAttribute();
                    return pgPool.withTransaction(sqlConnection -> upsertAttributeOperation
                            .upsertAttribute(sqlConnection, shard, attribute));
                })
                .map(SyncAttributeInternalResponse::new);
    }
}
