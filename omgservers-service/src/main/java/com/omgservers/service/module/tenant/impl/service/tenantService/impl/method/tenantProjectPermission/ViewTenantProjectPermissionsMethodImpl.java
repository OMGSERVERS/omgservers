package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.SelectActiveTenantProjectPermissionsByTenantProjectIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantProjectPermissionsMethodImpl implements ViewTenantProjectPermissionsMethod {

    final SelectActiveTenantProjectPermissionsByTenantProjectIdOperation
            selectActiveTenantProjectPermissionsByTenantProjectIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> execute(final ViewTenantProjectPermissionsRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantProjectId = request.getTenantProjectId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantProjectPermissionsByTenantProjectIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantProjectId
                                    ));
                })
                .map(ViewTenantProjectPermissionsResponse::new);

    }
}
