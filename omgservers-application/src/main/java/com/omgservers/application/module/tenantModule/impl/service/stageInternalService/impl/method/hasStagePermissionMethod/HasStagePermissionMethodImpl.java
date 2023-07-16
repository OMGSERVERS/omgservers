package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.hasStagePermissionMethod;

import com.omgservers.application.module.tenantModule.impl.operation.hasStagePermissionOperation.HasStagePermissionOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.HasStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
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
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request) {
        HasStagePermissionInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var stage = request.getStage();
                    final var user = request.getUser();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasStagePermissionOperation
                            .hasStagePermission(sqlConnection, shard, stage, user, permission));
                })
                .map(HasStagePermissionInternalResponse::new);
    }
}
