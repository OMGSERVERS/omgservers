package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.SelectActiveTenantStagePermissionsByTenantStageIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantStagePermissionsMethodImpl implements ViewTenantStagePermissionsMethod {

    final SelectActiveTenantStagePermissionsByTenantStageIdOperation
            selectActiveTenantStagePermissionsByTenantStageIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantStagePermissionsResponse> execute(final ShardModel shardModel,
                                                           final ViewTenantStagePermissionsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        return pgPool.withTransaction(sqlConnection ->
                        selectActiveTenantStagePermissionsByTenantStageIdOperation.execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                tenantStageId))
                .map(ViewTenantStagePermissionsResponse::new);

    }
}
