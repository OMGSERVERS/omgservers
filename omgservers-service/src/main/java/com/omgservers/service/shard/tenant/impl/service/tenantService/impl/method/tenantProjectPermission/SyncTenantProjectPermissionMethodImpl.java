package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission.UpsertTenantProjectPermissionOperation;
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
    final VerifyTenantProjectExistsOperation verifyTenantProjectExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantProjectPermissionResponse> execute(final ShardModel shardModel,
                                                            final SyncTenantProjectPermissionRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var permission = request.getTenantProjectPermission();
        final var tenantId = permission.getTenantId();
        final var tenantProjectId = permission.getProjectId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyTenantProjectExistsOperation
                                .execute(sqlConnection, shardModel.shard(), tenantId, tenantProjectId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantProjectPermissionOperation.execute(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                permission);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "project does not exist or was deleted, id=" + tenantProjectId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncTenantProjectPermissionResponse::new);
    }
}
