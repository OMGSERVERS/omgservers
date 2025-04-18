package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantPermission.SelectActiveTenantPermissionsByTenantIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantPermissionsResponse> execute(final ShardModel shardModel,
                                                      final ViewTenantPermissionsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        return pgPool.withTransaction(sqlConnection ->
                        selectActiveTenantPermissionsByTenantIdOperation.execute(sqlConnection,
                                shardModel.slot(),
                                tenantId))
                .map(ViewTenantPermissionsResponse::new);

    }
}
