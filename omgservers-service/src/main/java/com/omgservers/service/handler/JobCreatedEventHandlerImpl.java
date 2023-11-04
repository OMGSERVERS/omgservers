package com.omgservers.service.handler;

import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
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
public class JobCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (JobCreatedEventBodyModel) event.getBody();
        final var shardKey = body.getShardKey();
        final var entityId = body.getEntityId();
        final var jobQualifier = body.getJobQualifier();
        final var request = new ScheduleJobRequest(shardKey, entityId, jobQualifier);

        log.info("Job was created, qualifier={}, shardKey={}, entityId={}",
                jobQualifier, shardKey, entityId);

        return systemModule.getJobService().scheduleJob(request)
                .replaceWith(true);
    }
}
