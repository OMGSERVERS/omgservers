package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.viewTenantPermissions;

import com.omgservers.schema.module.tenant.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.selectActiveTenantPermissionsByTenantId.SelectActiveTenantPermissionsByTenantIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
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
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        log.debug("View tenant permissions, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveTenantPermissionsByTenantIdOperation
                            .selectActiveTenantPermissionsByTenantId(sqlConnection,
                                    shard.shard(),
                                    tenantId
                            ));
                })
                .map(ViewTenantPermissionsResponse::new);

    }
}
