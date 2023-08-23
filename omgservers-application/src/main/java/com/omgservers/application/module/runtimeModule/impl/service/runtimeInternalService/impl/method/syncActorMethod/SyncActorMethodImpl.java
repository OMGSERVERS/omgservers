package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncActorMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertActorOperation.UpsertActorOperation;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncActorInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncActorMethodImpl implements SyncActorMethod {

    final InternalModule internalModule;

    final UpsertActorOperation upsertActorOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncActorInternalResponse> syncActor(SyncActorInternalRequest request) {
        SyncActorInternalRequest.validate(request);

        final var actor = request.getActor();
        return changeOperation.changeWithLog(request,
                        (sqlConnection, shardModel) -> upsertActorOperation
                                .upsertActor(sqlConnection, shardModel.shard(), actor),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Actor was created, actor=" + actor);
                            } else {
                                return logModelFactory.create("Actor was update, actor=" + actor);
                            }
                        }
                )
                .map(SyncActorInternalResponse::new);
    }
}
