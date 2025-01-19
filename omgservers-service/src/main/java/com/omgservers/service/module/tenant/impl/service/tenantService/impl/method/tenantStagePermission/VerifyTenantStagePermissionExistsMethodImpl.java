package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.VerifyTenantStagePermissionExistsOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> execute(VerifyTenantStagePermissionExistsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantStageId = request.getTenantStageId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> verifyTenantStagePermissionExistsOperation
                            .execute(sqlConnection, shard, tenantId, tenantStageId, userId, permission));
                })
                .map(VerifyTenantStagePermissionExistsResponse::new);
    }
}
