package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.VerifyTenantStageExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.UpsertTenantStagePermissionOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantStagePermissionMethodImpl implements SyncTenantStagePermissionMethod {

    final UpsertTenantStagePermissionOperation upsertTenantStagePermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantStagePermissionResponse> execute(final SyncTenantStagePermissionRequest request) {
        log.debug("Sync tenant stage permission, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var permission = request.getTenantStagePermission();
        final var tenantId = permission.getTenantId();
        final var stageId = permission.getStageId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    verifyTenantStageExistsOperation.execute(sqlConnection, shard, tenantId, stageId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantStagePermissionOperation.execute(
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
                .map(SyncTenantStagePermissionResponse::new);
    }
}
