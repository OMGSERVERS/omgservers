package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionJenkinsRequestCreatedEventBodyModel;
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
public class VersionJenkinsRequestCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final JobService jobService;

    final EventModelFactory eventModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_JENKINS_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionJenkinsRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionJenkinsRequest(tenantId, id)
                .flatMap(versionJenkinsRequest -> {
                    final var versionId = versionJenkinsRequest.getVersionId();
                    final var qualifier = versionJenkinsRequest.getQualifier();
                    final var buildNumber = versionJenkinsRequest.getBuildNumber();
                    log.info("Version jenkins request was created, " +
                                    "id={}, version={}/{}, qualifier={}, buildNumber={}",
                            versionJenkinsRequest.getId(),
                            tenantId,
                            versionId,
                            qualifier,
                            buildNumber);

                    final var jenkinsRequestId = versionJenkinsRequest.getId();

                    final var idempotencyKey = event.getId().toString();
                    return syncJenkinsRequestJob(tenantId, jenkinsRequestId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<VersionJenkinsRequestModel> getVersionJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionJenkinsRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionJenkinsRequest(request)
                .map(GetVersionJenkinsRequestResponse::getVersionJenkinsRequest);
    }

    Uni<Boolean> syncJenkinsRequestJob(final Long tenantId,
                                       final Long jenkinsRequestId,
                                       final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.JENKINS_REQUEST,
                tenantId,
                jenkinsRequestId,
                idempotencyKey);

        final var request = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(request)
                .map(SyncJobResponse::getCreated);
    }
}