package com.omgservers.service.handler;

import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (JobDeletedEventBodyModel) event.getBody();
        final var jobId = body.getId();

        return getDeletedJob(jobId)
                .flatMap(job -> {
                    final var shardKey = job.getShardKey();
                    final var entityId = job.getEntityId();
                    final var qualifier = job.getQualifier();

                    log.info("Job was deleted, qualifier={}, entity={}/{}",
                            qualifier, shardKey, entityId);

                    final var request = new UnscheduleJobRequest(shardKey, entityId, qualifier);
                    return systemModule.getJobService().unscheduleJob(request);
                })
                .replaceWith(true);
    }

    Uni<JobModel> getDeletedJob(final Long id) {
        final var request = new GetJobRequest(id, true);
        return systemModule.getJobService().getJob(request)
                .map(GetJobResponse::getJob);
    }
}
