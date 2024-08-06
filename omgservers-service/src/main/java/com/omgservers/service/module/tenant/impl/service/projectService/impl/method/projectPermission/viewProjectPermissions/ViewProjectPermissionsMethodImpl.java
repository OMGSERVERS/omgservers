package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.viewProjectPermissions;

import com.omgservers.schema.module.tenant.ViewProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewProjectPermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.projectPermission.selectActiveProjectPermissionsByProjectId.SelectActiveProjectPermissionsByProjectIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewProjectPermissionsMethodImpl implements ViewProjectPermissionsMethod {

    final SelectActiveProjectPermissionsByProjectIdOperation selectActiveProjectPermissionsByProjectIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewProjectPermissionsResponse> viewProjectPermissions(final ViewProjectPermissionsRequest request) {
        log.debug("View project permission, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var projectId = request.getProjectId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveProjectPermissionsByProjectIdOperation
                            .selectActiveProjectPermissionsByProjectId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    projectId
                            ));
                })
                .map(ViewProjectPermissionsResponse::new);

    }
}
