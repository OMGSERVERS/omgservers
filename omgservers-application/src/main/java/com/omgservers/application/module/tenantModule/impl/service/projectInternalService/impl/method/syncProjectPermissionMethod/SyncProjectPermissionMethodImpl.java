package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectPermissionMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectPermissionOperation.UpsertProjectPermissionOperation;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectPermissionInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncProjectPermissionMethodImpl implements SyncProjectPermissionMethod {

    final InternalModule internalModule;

    final UpsertProjectPermissionOperation upsertProjectPermissionOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request) {
        SyncProjectPermissionInternalRequest.validate(request);

        final var permission = request.getPermission();
        return changeOperation.changeWithLog(request,
                        ((sqlConnection, shardModel) -> upsertProjectPermissionOperation
                                .upsertProjectPermission(sqlConnection, shardModel.shard(), permission)),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Project permission was created, " +
                                        "permission=" + permission);
                            } else {
                                return logModelFactory.create("Project permission was updated, " +
                                        "permission=" + permission);
                            }
                        })
                .map(SyncProjectPermissionInternalResponse::new);
    }
}
