package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.VerifyTenantStageExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.UpsertTenantStagePermissionOperation;
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
    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantStagePermissionResponse> execute(final ShardModel shardModel,
                                                          final SyncTenantStagePermissionRequest request) {
        log.debug("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var permission = request.getTenantStagePermission();
        final var tenantId = permission.getTenantId();
        final var tenantStageId = permission.getStageId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantStageExistsOperation.execute(sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        tenantStageId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantStagePermissionOperation.execute(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.slot(),
                                                permission);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "stage does not exist or was deleted, id=" + tenantStageId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncTenantStagePermissionResponse::new);
    }
}
