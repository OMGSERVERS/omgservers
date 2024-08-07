package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.hasProjectPermission;

import com.omgservers.schema.module.tenant.HasProjectPermissionRequest;
import com.omgservers.schema.module.tenant.HasProjectPermissionResponse;
import com.omgservers.service.module.tenant.impl.operation.projectPermission.hasProjectPermission.HasProjectPermissionOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasProjectPermissionMethodImpl implements HasProjectPermissionMethod {

    final HasProjectPermissionOperation hasProjectPermissionOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request) {
        log.debug("Has project permission, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var projectId = request.getProjectId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasProjectPermissionOperation
                            .hasProjectPermission(sqlConnection, shard.shard(), tenantId, projectId, userId, permission));
                })
                .map(HasProjectPermissionResponse::new);
    }
}
