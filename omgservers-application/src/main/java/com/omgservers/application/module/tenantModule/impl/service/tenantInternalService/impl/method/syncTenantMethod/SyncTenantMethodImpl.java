package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateTenantOperation.ValidateTenantOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantInternalRequest;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantMethodImpl implements SyncTenantMethod {

    final InternalModule internalModule;

    final ValidateTenantOperation validateTenantOperation;
    final UpsertTenantOperation upsertTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request) {
        SyncTenantInternalRequest.validate(request);

        final var tenant = request.getTenant();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> validateTenantOperation.validateTenant(tenant))
                .flatMap(validatedTenant -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> syncTenant(shardModel.shard(), tenant))
                .map(SyncTenantResponse::new);
    }

    Uni<Boolean> syncTenant(final Integer shard, final TenantModel tenant) {
        return pgPool.withTransaction(sqlConnection ->
                upsertTenantOperation.upsertTenant(sqlConnection, shard, tenant)
                        .call(inserted -> {
                            if (inserted) {
                                final var id = tenant.getId();
                                final var eventBody = new TenantCreatedEventBodyModel(id);
                                final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        }));
    }
}