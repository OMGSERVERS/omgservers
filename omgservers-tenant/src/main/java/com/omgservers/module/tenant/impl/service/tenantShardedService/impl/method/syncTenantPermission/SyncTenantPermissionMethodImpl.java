package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission;

import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.module.tenant.impl.operation.upsertTenantPermission.UpsertTenantPermissionOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
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
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionShardRequest request) {
        SyncTenantPermissionShardRequest.validate(request);

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
