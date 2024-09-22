package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantJenkinsRequestDeletedEventBodyModel;
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
public class TenantJenkinsRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final JobService jobService;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_JENKINS_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantJenkinsRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantJenkinsRequest(tenantId, id)
                .flatMap(tenantJenkinsRequest -> {
                    final var versionId = tenantJenkinsRequest.getVersionId();
                    final var qualifier = tenantJenkinsRequest.getQualifier();
                    log.info("Tenant jenkins request was deleted, id={}, version={}/{}, qualifier={}",
                            tenantJenkinsRequest.getId(),
                            tenantId,
                            versionId,
                            qualifier);

                    return findAndDeleteJob(tenantId, id);
                })
                .replaceWithVoid();
    }

    Uni<TenantJenkinsRequestModel> getTenantJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantJenkinsRequest(request)
                .map(GetTenantJenkinsRequestResponse::getTenantJenkinsRequest);
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
