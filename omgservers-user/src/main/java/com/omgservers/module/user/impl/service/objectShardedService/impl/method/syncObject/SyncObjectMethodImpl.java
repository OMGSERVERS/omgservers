package com.omgservers.module.user.impl.service.objectShardedService.impl.method.syncObject;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.upsertObject.UpsertObjectOperation;
import com.omgservers.module.user.impl.operation.validateObject.ValidateObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncObjectMethodImpl implements SyncObjectMethod {

    final InternalModule internalModule;

    final ValidateObjectOperation validateObjectOperation;
    final UpsertObjectOperation upsertObjectOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request) {
        SyncObjectShardedRequest.validate(request);

        final var userId = request.getUserId();
        final var object = request.getObject();
        validateObjectOperation.validateObject(object);
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        ((sqlConnection, shardModel) -> upsertObjectOperation
                                .upsertObject(sqlConnection, shardModel.shard(), object)),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create(String.format("Object was inserted, " +
                                        "userId=%d, object=%s", userId, object));
                            } else {
                                return logModelFactory.create(String.format("Object was updated, " +
                                        "userId=%d, object=%s", userId, object));
                            }
                        }))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncObjectShardedResponse::new);
    }
}
