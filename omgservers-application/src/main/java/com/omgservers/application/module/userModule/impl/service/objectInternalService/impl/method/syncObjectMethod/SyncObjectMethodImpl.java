package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod;

import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation.UpsertObjectOperation;
import com.omgservers.application.module.userModule.impl.operation.validateObjectOperation.ValidateObjectOperation;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncObjectMethodImpl implements SyncObjectMethod {

    final ValidateObjectOperation validateObjectOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertObjectOperation upsertObjectOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> syncObject(SyncObjectInternalRequest request) {
        SyncObjectInternalRequest.validate(request);

        final var user = request.getUserId();
        final var object = request.getObject();
        return Uni.createFrom().voidItem()
                .map(voidItem -> validateObjectOperation.validateObject(object))
                .flatMap(validatedObject -> checkShardOperation.checkShard(user.toString())
                        .flatMap(shardModel -> {
                            final var shard = shardModel.shard();
                            return pgPool.withTransaction(sqlConnection -> upsertObjectOperation
                                    .upsertObject(sqlConnection, shard, validatedObject));
                        }))
                .replaceWithVoid();
    }
}
