package com.omgservers.service.handler.impl.service;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.system.JobDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.server.job.JobService;
import com.omgservers.service.server.job.dto.GetJobRequest;
import com.omgservers.service.server.job.dto.GetJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobDeletedEventHandlerImpl implements EventHandler {

    final JobService jobService;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (JobDeletedEventBodyModel) event.getBody();
        final var jobId = body.getId();

        return getJob(jobId)
                .flatMap(job -> {
                    log.debug("Deleted, {}", job);
                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<JobModel> getJob(final Long id) {
        final var request = new GetJobRequest(id);
        return jobService.getJob(request)
                .map(GetJobResponse::getJob);
    }
}
