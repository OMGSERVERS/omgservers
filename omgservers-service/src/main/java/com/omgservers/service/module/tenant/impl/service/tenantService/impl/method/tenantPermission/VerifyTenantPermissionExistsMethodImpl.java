package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.VerifyTenantPermissionExistsOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyTenantPermissionExistsMethodImpl implements VerifyTenantPermissionExistsMethod {

    final VerifyTenantPermissionExistsOperation verifyTenantPermissionExistsOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> execute(VerifyTenantPermissionExistsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var userId = request.getUserId();
                    final var permission = request.getQualifier();
                    return pgPool.withTransaction(sqlConnection -> verifyTenantPermissionExistsOperation
                            .execute(sqlConnection, shard.shard(), tenantId, userId, permission));
                })
                .map(VerifyTenantPermissionExistsResponse::new);
    }
}
