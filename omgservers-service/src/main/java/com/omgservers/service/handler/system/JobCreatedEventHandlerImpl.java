package com.omgservers.service.handler.system;

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
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (JobCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        return systemModule.getShortcutService().getJob(id)
                .flatMap(job -> {
                    final var shardKey = job.getShardKey();
                    final var entityId = job.getEntityId();
                    final var qualifier = job.getQualifier();
                    log.info("Job was created, id={}, qualifier={}, entity={}/{}",
                            id, qualifier, shardKey, entityId);

                    return systemModule.getShortcutService().scheduleJob(shardKey, entityId, qualifier);
                })
                .replaceWith(true);
    }
}
