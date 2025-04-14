package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.VerifyTenantStagePermissionExistsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyTenantStagePermissionExistsMethodImpl implements VerifyTenantStagePermissionExistsMethod {

    final VerifyTenantStagePermissionExistsOperation verifyTenantStagePermissionExistsOperation;
    final PgPool pgPool;

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> execute(final ShardModel shardModel,
                                                                  final VerifyTenantStagePermissionExistsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        final var userId = request.getUserId();
        final var permission = request.getPermission();
        return pgPool.withTransaction(sqlConnection -> verifyTenantStagePermissionExistsOperation
                        .execute(sqlConnection, shardModel.shard(), tenantId, tenantStageId, userId, permission))
                .map(VerifyTenantStagePermissionExistsResponse::new);
    }
}
