package com.omgservers.service.handler.system;

import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (JobCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        return getJob(id)
                .flatMap(job -> {
                    final var shardKey = job.getShardKey();
                    final var entityId = job.getEntityId();
                    final var qualifier = job.getQualifier();
                    log.info("Job was created, id={}, qualifier={}, entity={}/{}",
                            id, qualifier, shardKey, entityId);

                    return scheduleJob(shardKey, entityId, qualifier);
                })
                .replaceWithVoid();
    }

    Uni<JobModel> getJob(final Long id) {
        final var request = new GetJobRequest(id);
        return systemModule.getJobService().getJob(request)
                .map(GetJobResponse::getJob);
    }

    Uni<Void> scheduleJob(final Long shardKey,
                          final Long entityId,
                          final JobQualifierEnum qualifier) {
        final var request = new ScheduleJobRequest(shardKey, entityId, qualifier);
        return systemModule.getJobService().scheduleJob(request);
    }
}
