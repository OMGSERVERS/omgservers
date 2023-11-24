package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.viewProjectPermissions;

import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.selectActiveProjectPermissionsByProjectId.SelectActiveProjectPermissionsByProjectIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
