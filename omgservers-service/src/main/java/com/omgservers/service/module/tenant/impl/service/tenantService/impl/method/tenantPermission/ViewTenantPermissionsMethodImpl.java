package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.SelectActiveTenantPermissionsByTenantIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantPermissionsMethodImpl implements ViewTenantPermissionsMethod {

    final SelectActiveTenantPermissionsByTenantIdOperation selectActiveTenantPermissionsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantPermissionsResponse> execute(final ViewTenantPermissionsRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveTenantPermissionsByTenantIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    tenantId
                            ));
                })
                .map(ViewTenantPermissionsResponse::new);

    }
}
