package com.omgservers.service.handler.system;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ContainerCreatedEventBodyModel;
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
public class ContainerCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CONTAINER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ContainerCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        log.info("Container was created, id={}", id);

        return systemModule.getShortcutService().getContainer(id)
                .flatMap(container -> systemModule.getShortcutService().runContainer(container))
                .replaceWith(true);
    }
}
