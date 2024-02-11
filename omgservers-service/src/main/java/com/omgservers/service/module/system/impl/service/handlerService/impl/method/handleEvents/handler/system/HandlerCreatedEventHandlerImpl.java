package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.system;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.HandlerCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.factory.JobModelFactory;
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
public class HandlerCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.HANDLER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (HandlerCreatedEventBodyModel) event.getBody();
        final var handlerId = body.getId();

        return systemModule.getShortcutService().getHandler(handlerId)
                .flatMap(handler -> {
                    log.info("Handler was created, handlerId={}", handlerId);
                    return syncHandlerJob(handlerId);
                })
                .replaceWithVoid();
    }

    Uni<Boolean> syncHandlerJob(final Long handlerId) {
        final var job = jobModelFactory.create(handlerId, handlerId, JobQualifierEnum.HANDLER);
        return systemModule.getShortcutService().syncJob(job);
    }
}
