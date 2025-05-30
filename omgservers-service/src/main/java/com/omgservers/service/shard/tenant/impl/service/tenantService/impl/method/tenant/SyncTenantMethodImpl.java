package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.SyncTenantResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenant.UpsertTenantOperation;
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
    final UpsertTenantOperation upsertTenantOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantResponse> syncTenant(final ShardModel shardModel,
                                              final SyncTenantRequest request) {
        log.debug("{}", request);

        final var tenant = request.getTenant();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertTenantOperation.execute(changeContext, sqlConnection, shardModel.slot(), tenant))
                .map(ChangeContext::getResult)
                .map(SyncTenantResponse::new);
    }
}