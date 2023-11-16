package com.omgservers.service.handler;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
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
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ContainerCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        log.info("Container was created, id={}", id);

        return getContainer(id)
                .flatMap(this::runContainer)
                .replaceWith(true);
    }

    Uni<ContainerModel> getContainer(final Long id) {
        final var request = new GetContainerRequest(id);
        return systemModule.getContainerService().getContainer(request)
                .map(GetContainerResponse::getContainer);
    }

    Uni<Boolean> runContainer(final ContainerModel container) {
        final var request = new RunContainerRequest(container);
        return systemModule.getContainerService().runContainer(request)
                .map(RunContainerResponse::getRan);
    }
}
