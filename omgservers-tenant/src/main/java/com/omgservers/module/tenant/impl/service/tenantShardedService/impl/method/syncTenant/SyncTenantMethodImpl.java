package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant;

import com.omgservers.ChangeContext;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.module.tenant.impl.operation.validateTenant.ValidateTenantOperation;
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
class SyncTenantMethodImpl implements SyncTenantMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final ValidateTenantOperation validateTenantOperation;
    final UpsertTenantOperation upsertTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request) {
        SyncTenantShardedRequest.validate(request);

        final var tenant = request.getTenant();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenant))
                .map(SyncTenantShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, TenantModel tenantModel) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertTenantOperation.upsertTenant(changeContext, sqlConnection, shardModel.shard(), tenantModel))
                .map(ChangeContext::getResult);
    }
}