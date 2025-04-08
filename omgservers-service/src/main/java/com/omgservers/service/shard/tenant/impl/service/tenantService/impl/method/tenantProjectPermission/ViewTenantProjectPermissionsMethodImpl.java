package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission.SelectActiveTenantProjectPermissionsByTenantProjectIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> execute(final ShardModel shardModel,
                                                             final ViewTenantProjectPermissionsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        return pgPool.withTransaction(sqlConnection ->
                        selectActiveTenantProjectPermissionsByTenantProjectIdOperation.execute(sqlConnection,
                                shardModel.shard(),
                                tenantId,
                                tenantProjectId
                        ))
                .map(ViewTenantProjectPermissionsResponse::new);

    }
}
