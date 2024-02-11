package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.system;

import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.EventHandler;
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
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (JobDeletedEventBodyModel) event.getBody();
        final var jobId = body.getId();

        return systemModule.getShortcutService().getJob(jobId)
                .flatMap(job -> {
                    final var shardKey = job.getShardKey();
                    final var entityId = job.getEntityId();
                    final var qualifier = job.getQualifier();

                    log.info("Job was deleted, qualifier={}, entity={}/{}",
                            qualifier, shardKey, entityId);

                    final var request = new UnscheduleJobRequest(shardKey, entityId, qualifier);
                    return systemModule.getJobService().unscheduleJob(request);
                })
                .replaceWithVoid();
    }
}
