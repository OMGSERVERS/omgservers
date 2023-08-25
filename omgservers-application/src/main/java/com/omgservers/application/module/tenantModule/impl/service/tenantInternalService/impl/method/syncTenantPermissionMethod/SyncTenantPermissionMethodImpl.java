package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantPermissionOperation.UpsertTenantPermissionOperation;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.tenantModule.SyncTenantPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
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
