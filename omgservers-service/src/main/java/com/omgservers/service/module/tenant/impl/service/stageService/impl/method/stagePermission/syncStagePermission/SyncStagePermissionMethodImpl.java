package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.syncStagePermission;

import com.omgservers.schema.module.tenant.SyncStagePermissionRequest;
import com.omgservers.schema.module.tenant.SyncStagePermissionResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.stage.hasStage.HasStageOperation;
import com.omgservers.service.module.tenant.impl.operation.stagePermission.upsertStagePermission.UpsertStagePermissionOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncStagePermissionMethodImpl implements SyncStagePermissionMethod {

    final UpsertStagePermissionOperation upsertStagePermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasStageOperation hasStageOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncStagePermissionResponse> syncStagePermission(final SyncStagePermissionRequest request) {
        log.debug("Sync stage permission, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var permission = request.getPermission();
        final var tenantId = permission.getTenantId();
        final var stageId = permission.getStageId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    hasStageOperation.hasStage(sqlConnection, shard, tenantId, stageId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertStagePermissionOperation.upsertStagePermission(
                                                            changeContext,
                                                            sqlConnection,
                                                            shardModel.shard(),
                                                            permission);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "stage does not exist or was deleted, id=" + stageId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncStagePermissionResponse::new);
    }
}
