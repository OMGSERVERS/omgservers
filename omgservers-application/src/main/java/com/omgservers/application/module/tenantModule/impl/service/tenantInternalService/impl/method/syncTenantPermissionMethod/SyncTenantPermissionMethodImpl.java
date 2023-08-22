package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantPermissionOperation.UpsertTenantPermissionOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantPermissionResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantPermissionMethodImpl implements SyncTenantPermissionMethod {

    final InternalModule internalModule;

    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionInternalRequest request) {
        SyncTenantPermissionInternalRequest.validate(request);

        final var permission = request.getPermission();
        return changeOperation.changeWithLog(request,
                        (sqlConnection, shardModel) -> upsertTenantPermissionOperation
                                .upsertTenantPermission(sqlConnection, shardModel.shard(), permission),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Tenant permission was created, " +
                                        "permission=" + permission);
                            } else {
                                return logModelFactory.create("Tenant permission was updated, " +
                                        "permission=" + permission);
                            }
                        }
                )
                .map(SyncTenantPermissionResponse::new);
    }
}
