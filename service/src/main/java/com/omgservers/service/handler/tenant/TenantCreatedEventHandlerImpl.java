package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.model.dto.system.job.SyncJobRequest;
import com.omgservers.model.dto.system.job.SyncJobResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.rootEntityRef.RootEntityRefQualifierEnum;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.factory.root.RootEntityRefModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final SystemModule systemModule;
    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    final RootEntityRefModelFactory rootEntityRefModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return getTenant(tenantId)
                .flatMap(tenant -> {
                    log.info("Tenant was created, tenant={}", tenantId);

                    final var idempotencyKey = event.getIdempotencyKey();

                    return syncRootEntityRef(tenantId, idempotencyKey)
                            .flatMap(created -> syncTenantJob(tenantId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getTenantService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Boolean> syncRootEntityRef(final Long tenantId,
                                   final String idempotencyKey) {
        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();
        final var rootEntityRef = rootEntityRefModelFactory.create(idempotencyKey, rootId,
                RootEntityRefQualifierEnum.TENANT,
                tenantId);
        final var request = new SyncRootEntityRefRequest(rootEntityRef);
        return rootModule.getRootService().syncRootEntityRefWithIdempotency(request)
                .map(SyncRootEntityRefResponse::getCreated);
    }

    Uni<Boolean> syncTenantJob(final Long tenantId,
                               final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.TENANT, tenantId, idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return systemModule.getJobService().syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
