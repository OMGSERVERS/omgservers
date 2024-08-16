package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionJenkinsRequestDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.DeleteJobRequest;
import com.omgservers.service.service.job.dto.DeleteJobResponse;
import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionJenkinsRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final JobService jobService;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_JENKINS_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionJenkinsRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionJenkinsRequest(tenantId, id)
                .flatMap(versionJenkinsRequest -> {
                    final var versionId = versionJenkinsRequest.getVersionId();
                    final var qualifier = versionJenkinsRequest.getQualifier();
                    log.info("Version jenkins request was deleted, id={}, version={}/{}, qualifier={}",
                            versionJenkinsRequest.getId(),
                            tenantId,
                            versionId,
                            qualifier);

                    return findAndDeleteJob(tenantId, id);
                })
                .replaceWithVoid();
    }

    Uni<VersionJenkinsRequestModel> getVersionJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionJenkinsRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionJenkinsRequest(request)
                .map(GetVersionJenkinsRequestResponse::getVersionJenkinsRequest);
    }

    Uni<Void> findAndDeleteJob(final Long tenantId, final Long jenkinsRequestId) {
        return findJob(tenantId, jenkinsRequestId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long tenantId, final Long jenkinsRequestId) {
        final var request = new FindJobRequest(tenantId, jenkinsRequestId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
