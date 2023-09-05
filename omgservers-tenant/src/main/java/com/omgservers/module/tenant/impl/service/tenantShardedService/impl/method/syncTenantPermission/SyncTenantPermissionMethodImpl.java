package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertTenantPermission.UpsertTenantPermissionOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantPermissionMethodImpl implements SyncTenantPermissionMethod {

    final InternalModule internalModule;

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(final SyncTenantPermissionShardedRequest request) {
        SyncTenantPermissionShardedRequest.validate(request);

        final var permission = request.getPermission();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, permission))
                .map(SyncTenantPermissionShardedResponse::new);
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
