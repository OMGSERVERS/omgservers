package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantPermission.VerifyTenantPermissionExistsOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> execute(final ShardModel shardModel,
                                                             final VerifyTenantPermissionExistsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var userId = request.getUserId();
        final var permission = request.getQualifier();
        return pgPool.withTransaction(sqlConnection -> verifyTenantPermissionExistsOperation
                        .execute(sqlConnection, shardModel.slot(), tenantId, userId, permission))
                .map(VerifyTenantPermissionExistsResponse::new);
    }
}
