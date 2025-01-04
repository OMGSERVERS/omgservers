package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.module.tenant.impl.operation.tenant.VerifyTenantExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.UpsertTenantPermissionOperation;
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
class SyncTenantPermissionMethodImpl implements SyncTenantPermissionMethod {

    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final VerifyTenantExistsOperation verifyTenantExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> execute(final SyncTenantPermissionRequest request) {
        log.trace("{}", request);

        final var permission = request.getTenantPermission();
        final var tenantId = permission.getTenantId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> verifyTenantExistsOperation
                                        .execute(sqlConnection, shardModel.shard(), tenantId)
                                        .flatMap(exists -> {
                                            if (exists) {
                                                return upsertTenantPermissionOperation.execute(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        permission);
                                            } else {
                                                throw new ServerSideNotFoundException(
                                                        ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                        "tenant does not exist or was deleted, id=" + tenantId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncTenantPermissionResponse::new);
    }
}
