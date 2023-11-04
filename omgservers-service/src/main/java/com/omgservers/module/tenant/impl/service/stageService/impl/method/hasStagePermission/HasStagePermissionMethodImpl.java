package com.omgservers.module.tenant.impl.service.stageService.impl.method.hasStagePermission;

import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.module.tenant.impl.operation.hasStagePermission.HasStagePermissionOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasStagePermissionMethodImpl implements HasStagePermissionMethod {

    final HasStagePermissionOperation hasStagePermissionOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasStagePermissionOperation
                            .hasStagePermission(sqlConnection, shard, tenantId, stageId, userId, permission));
                })
                .map(HasStagePermissionResponse::new);
    }
}