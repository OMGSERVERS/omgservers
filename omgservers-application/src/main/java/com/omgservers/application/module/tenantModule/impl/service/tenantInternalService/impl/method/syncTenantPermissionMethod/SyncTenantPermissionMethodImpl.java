package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod;

import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantPermissionOperation.UpsertTenantPermissionOperation;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.tenantModule.SyncTenantPermissionRoutedRequest;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRoutedRequest request) {
        SyncTenantPermissionRoutedRequest.validate(request);

        final var permission = request.getPermission();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
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
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncTenantPermissionResponse::new);
    }
}
