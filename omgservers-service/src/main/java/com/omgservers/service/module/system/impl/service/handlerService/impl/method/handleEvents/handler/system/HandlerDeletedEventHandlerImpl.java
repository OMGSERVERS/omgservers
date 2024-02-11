package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.system;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.HandlerDeletedEventBodyModel;
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
public class HandlerDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.HANDLER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (HandlerDeletedEventBodyModel) event.getBody();
        final var handlerId = body.getId();

        return systemModule.getShortcutService().getHandler(handlerId)
                .flatMap(stage -> {
                    log.info("Handler was deleted, handlerId={}", handlerId);

                    return deleteHandlerJob(handlerId);
                })
                .replaceWithVoid();
    }

    Uni<Boolean> deleteHandlerJob(final Long handlerId) {
        return systemModule.getShortcutService().findHandlerJob(handlerId)
                .flatMap(job -> systemModule.getShortcutService().deleteJob(job.getId()));
    }
}
