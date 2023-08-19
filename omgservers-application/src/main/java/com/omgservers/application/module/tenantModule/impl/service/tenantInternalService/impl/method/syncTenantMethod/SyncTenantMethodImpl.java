package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation.UpsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateTenantOperation.ValidateTenantOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
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
                        })
                        .call(inserted -> {
                            final LogModel syncLog;
                            if (inserted) {
                                syncLog = logModelFactory.create("Tenant was created, tenant=" + tenant);
                            } else {
                                syncLog = logModelFactory.create("Tenant was updated, tenant=" + tenant);
                            }
                            final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                            return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                        }));
    }
}