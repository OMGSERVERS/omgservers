package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod;

import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantPermissionOperation.UpsertTenantPermissionOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantPermissionMethodImpl implements SyncTenantPermissionMethod {

    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionInternalRequest request) {
        SyncTenantPermissionInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> upsertTenantPermissionOperation
                            .upsertTenantPermission(sqlConnection, shard.shard(), permission));
                })
                .map(SyncTenantPermissionResponse::new);
    }
}
