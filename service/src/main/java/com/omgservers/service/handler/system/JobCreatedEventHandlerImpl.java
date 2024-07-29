package com.omgservers.service.handler.system;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.system.JobCreatedEventBodyModel;
import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.service.system.job.GetJobRequest;
import com.omgservers.schema.service.system.job.GetJobResponse;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.server.service.job.JobService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobCreatedEventHandlerImpl implements EventHandler {

    final JobService jobService;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (JobCreatedEventBodyModel) event.getBody();
        final var jobId = body.getId();

        return getJob(jobId)
                .flatMap(job -> {
                    log.info("Job was created, jobId={}, qualifier={}", jobId, job.getQualifier());
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
