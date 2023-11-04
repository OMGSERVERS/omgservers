package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.syncTenantPermission;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertTenantPermission.UpsertTenantPermissionOperation;
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

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        final var permission = request.getPermission();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, permission))
                .map(SyncTenantPermissionResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, TenantPermissionModel permission) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertTenantPermissionOperation.upsertTenantPermission(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                permission))
                .map(ChangeContext::getResult);
    }
}
