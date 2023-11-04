package com.omgservers.service.handler;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import com.omgservers.model.dto.system.StopContainerRequest;
import com.omgservers.model.dto.system.StopContainerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ContainerDeletedEventBodyModel;
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
public class ContainerDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CONTAINER_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ContainerDeletedEventBodyModel) event.getBody();
        final var id = body.getId();

        log.info("Container was deleted, id={}", id);

        return getDeletedContainer(id)
                .flatMap(container -> stopContainer(id))
                .replaceWith(true);
    }

    Uni<ContainerModel> getDeletedContainer(final Long id) {
        final var request = new GetContainerRequest(id, true);
        return systemModule.getContainerService().getContainer(request)
                .map(GetContainerResponse::getContainer);
    }

    Uni<Boolean> stopContainer(final Long id) {
        final var request = new StopContainerRequest(id, true);
        return systemModule.getContainerService().stopContainer(request)
                .map(StopContainerResponse::getStopped);
    }
}
