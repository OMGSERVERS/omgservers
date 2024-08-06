package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.hasTenantPermission;

import com.omgservers.schema.module.tenant.HasTenantPermissionRequest;
import com.omgservers.schema.module.tenant.HasTenantPermissionResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.hasTenantPermission.HasTenantPermissionOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasTenantPermissionMethodImpl implements HasTenantPermissionMethod {

    final HasTenantPermissionOperation hasTenantPermissionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request) {
        log.debug("Has tenant permission, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasTenantPermissionOperation
                            .hasTenantPermission(sqlConnection, shard.shard(), tenantId, userId, permission));
                })
                .map(HasTenantPermissionResponse::new);
    }
}
