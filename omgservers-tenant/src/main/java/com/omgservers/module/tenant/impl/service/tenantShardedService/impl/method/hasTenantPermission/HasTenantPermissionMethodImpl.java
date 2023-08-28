package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.hasTenantPermission;

import com.omgservers.module.tenant.impl.operation.hasTenantPermission.HasTenantPermissionOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
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
    public Uni<HasTenantPermissionShardedResponse> hasTenantPermission(HasTenantPermissionShardedRequest request) {
        HasTenantPermissionShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasTenantPermissionOperation
                            .hasTenantPermission(sqlConnection, shard.shard(), tenantId, userId, permission));
                })
                .map(HasTenantPermissionShardedResponse::new);
    }
}
