package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.TenantUpdatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateTenantOperation.ValidateTenantOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request) {
        SyncTenantInternalRequest.validate(request);

        final var tenant = request.getTenant();
        validateTenantOperation.validateTenant(tenant);
        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncTenantResponse::new);
    }
}