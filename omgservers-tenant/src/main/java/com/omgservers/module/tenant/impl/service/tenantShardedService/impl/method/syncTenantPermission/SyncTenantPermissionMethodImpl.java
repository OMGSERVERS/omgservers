package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission;

import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.module.tenant.impl.operation.upsertTenantPermission.UpsertTenantPermissionOperation;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
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
    public Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(final SyncTenantPermissionShardedRequest request) {
        SyncTenantPermissionShardedRequest.validate(request);

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
                .map(SyncTenantPermissionShardedResponse::new);
    }
}
