package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProjectPermission;

import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.module.tenant.impl.operation.upsertProjectPermission.UpsertProjectPermissionOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request) {
        SyncProjectPermissionShardRequest.validate(request);

        final var permission = request.getPermission();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
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
                        }))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncProjectPermissionInternalResponse::new);
    }
}
