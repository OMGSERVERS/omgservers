package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod;

import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation.UpsertObjectOperation;
import com.omgservers.application.module.userModule.impl.operation.validateObjectOperation.ValidateObjectOperation;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.SyncObjectInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncObjectMethodImpl implements SyncObjectMethod {

    final ValidateObjectOperation validateObjectOperation;
    final UpsertObjectOperation upsertObjectOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request) {
        SyncObjectInternalRequest.validate(request);

        final var userId = request.getUserId();
        final var object = request.getObject();
        validateObjectOperation.validateObject(object);
        return changeOperation.changeWithLog(request,
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
                        })
                .map(SyncObjectInternalResponse::new);
    }
}
