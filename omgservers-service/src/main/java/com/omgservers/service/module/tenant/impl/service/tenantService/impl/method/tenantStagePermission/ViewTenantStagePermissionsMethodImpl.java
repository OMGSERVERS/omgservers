package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.SelectActiveTenantStagePermissionsByTenantStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantStagePermissionsResponse> execute(final ViewTenantStagePermissionsRequest request) {
        log.debug("View tenant stage permissions, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantStagePermissionsByTenantStageIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            stageId
                                    ));
                })
                .map(ViewTenantStagePermissionsResponse::new);

    }
}
