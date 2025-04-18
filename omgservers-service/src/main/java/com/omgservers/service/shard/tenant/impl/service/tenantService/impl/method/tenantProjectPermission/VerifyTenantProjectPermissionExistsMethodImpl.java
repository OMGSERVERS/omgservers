package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission.VerifyTenantProjectPermissionExistsOperation;
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
    final PgPool pgPool;

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> execute(final ShardModel shardModel,
                                                                    final VerifyTenantProjectPermissionExistsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        final var userId = request.getUserId();
        final var permission = request.getTenantProjectPermissionQualifier();
        return pgPool.withTransaction(sqlConnection -> verifyTenantProjectPermissionExistsOperation
                        .execute(sqlConnection, shardModel.slot(), tenantId, tenantProjectId, userId, permission))
                .map(VerifyTenantProjectPermissionExistsResponse::new);
    }
}
