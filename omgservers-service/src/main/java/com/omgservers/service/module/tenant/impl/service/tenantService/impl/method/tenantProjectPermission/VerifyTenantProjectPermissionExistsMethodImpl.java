package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.VerifyTenantProjectPermissionExistsOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyTenantProjectPermissionExistsMethodImpl implements VerifyTenantProjectPermissionExistsMethod {

    final VerifyTenantProjectPermissionExistsOperation verifyTenantProjectPermissionExistsOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> execute(
            VerifyTenantProjectPermissionExistsRequest request) {
        log.debug("Has tenant project permission, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantProjectId = request.getTenantProjectId();
                    final var userId = request.getUserId();
                    final var permission = request.getTenantProjectPermissionQualifier();
                    return pgPool.withTransaction(sqlConnection -> verifyTenantProjectPermissionExistsOperation
                            .execute(sqlConnection, shard.shard(), tenantId, tenantProjectId, userId, permission));
                })
                .map(VerifyTenantProjectPermissionExistsResponse::new);
    }
}
