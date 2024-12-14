package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.UpsertTenantProjectPermissionOperation;
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
class SyncTenantProjectPermissionMethodImpl implements SyncTenantProjectPermissionMethod {

    final UpsertTenantProjectPermissionOperation upsertTenantProjectPermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantProjectExistsOperation verifyTenantProjectExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantProjectPermissionResponse> execute(final SyncTenantProjectPermissionRequest request) {
        log.trace("Requested, {}", request);

        final var shardKey = request.getRequestShardKey();
        final var permission = request.getTenantProjectPermission();
        final var tenantId = permission.getTenantId();
        final var tenantProjectId = permission.getProjectId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantProjectExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantProjectId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantProjectPermissionOperation.execute(
                                                            changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            permission);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "project does not exist or was deleted, id=" + tenantProjectId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantProjectPermissionResponse::new);
    }
}
