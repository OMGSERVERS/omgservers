package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant;

import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.module.tenant.impl.operation.validateTenant.ValidateTenantOperation;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantUpdatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantMethodImpl implements SyncTenantMethod {

    final InternalModule internalModule;

    final ValidateTenantOperation validateTenantOperation;
    final UpsertTenantOperation upsertTenantOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request) {
        SyncTenantShardedRequest.validate(request);

        final var tenant = request.getTenant();
        validateTenantOperation.validateTenant(tenant);
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertTenantOperation
                                .upsertTenant(sqlConnection, shardModel.shard(), tenant),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Tenant was created, tenant=" + tenant);
                            } else {
                                return logModelFactory.create("Tenant was updated, tenant=" + tenant);
                            }
                        },
                        inserted -> {
                            final var id = tenant.getId();
                            if (inserted) {
                                return new TenantCreatedEventBodyModel(id);
                            } else {
                                return new TenantUpdatedEventBodyModel(id);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncTenantShardedResponse::new);
    }
}