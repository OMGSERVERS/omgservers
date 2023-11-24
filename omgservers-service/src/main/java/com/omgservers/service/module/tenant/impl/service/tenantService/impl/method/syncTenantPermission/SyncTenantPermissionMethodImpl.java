package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.syncTenantPermission;

import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.impl.operation.hasTenant.HasTenantOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertTenantPermission.UpsertTenantPermissionOperation;
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

    final SystemModule systemModule;

    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasTenantOperation hasTenantOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        log.debug("Sync tenant permission, request={}", request);

        final var permission = request.getPermission();
        final var tenantId = permission.getTenantId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> hasTenantOperation
                                        .hasTenant(sqlConnection, shardModel.shard(), tenantId)
                                        .flatMap(has -> {
                                            if (has) {
                                                return upsertTenantPermissionOperation.upsertTenantPermission(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        permission);
                                            } else {
                                                throw new ServerSideConflictException(
                                                        "tenant does not exist or was deleted, id=" + tenantId);
                                            }
                                        })
                        )
                        .map(ChangeContext::getResult))
                .map(SyncTenantPermissionResponse::new);
    }
}
