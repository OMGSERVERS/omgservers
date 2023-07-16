package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.createTenantModel;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.tenantModule.impl.operation.insertTenantOperation.InsertTenantOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateTenantOperation.ValidateTenantOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.internalModule.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.CreateTenantInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantMethodImpl implements CreateTenantMethod {

    final InternalModule internalModule;

    final ValidateTenantOperation validateTenantOperation;
    final InsertTenantOperation insertTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> createTenant(CreateTenantInternalRequest request) {
        CreateTenantInternalRequest.validate(request);

        final var tenant = request.getTenant();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> validateTenantOperation.validateTenant(tenant))
                .flatMap(validatedTenant -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> createTenant(shardModel.shard(), tenant));
    }

    Uni<Void> createTenant(final Integer shard, final TenantModel tenant) {
        return pgPool.withTransaction(sqlConnection ->
                insertTenantOperation.insertTenant(sqlConnection, shard, tenant)
                        .call(voidItem -> {
                            final var uuid = tenant.getUuid();
                            final var origin = TenantCreatedEventBodyModel.createEvent(uuid);
                            final var event = EventCreatedEventBodyModel.createEvent(origin);
                            final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                            return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                        }));
    }
}