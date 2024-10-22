package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantBuildRequestCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final JobService jobService;

    final EventModelFactory eventModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_BUILD_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantBuildRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantBuildRequest(tenantId, id)
                .flatMap(tenantBuildRequest -> {
                    log.info("Created, {}", tenantBuildRequest);

                    final var tenantBuildRequestId = tenantBuildRequest.getId();

                    final var idempotencyKey = event.getId().toString();
                    return syncJob(tenantId, tenantBuildRequestId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<TenantBuildRequestModel> getTenantBuildRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantBuildRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantBuildRequest(request)
                .map(GetTenantBuildRequestResponse::getTenantBuildRequest);
    }

    Uni<Boolean> syncJob(final Long tenantId,
                         final Long tenantBuildRequestId,
                         final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.BUILD_REQUEST,
                tenantId,
                tenantBuildRequestId,
                idempotencyKey);

        final var request = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(request)
                .map(SyncJobResponse::getCreated);
    }
}
