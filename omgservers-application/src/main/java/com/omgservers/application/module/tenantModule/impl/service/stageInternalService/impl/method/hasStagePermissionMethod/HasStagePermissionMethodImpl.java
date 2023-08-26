package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.hasStagePermissionMethod;

import com.omgservers.application.module.tenantModule.impl.operation.hasStagePermissionOperation.HasStagePermissionOperation;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
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
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request) {
        HasStagePermissionRoutedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var stageId = request.getStageId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasStagePermissionOperation
                            .hasStagePermission(sqlConnection, shard, stageId, userId, permission));
                })
                .map(HasStagePermissionInternalResponse::new);
    }
}
