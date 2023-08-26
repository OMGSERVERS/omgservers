package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod;

import com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation.UpsertObjectOperation;
import com.omgservers.application.module.userModule.impl.operation.validateObjectOperation.ValidateObjectOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.userModule.SyncObjectShardRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
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
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request) {
        SyncObjectShardRequest.validate(request);

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
                .map(SyncObjectInternalResponse::new);
    }
}
