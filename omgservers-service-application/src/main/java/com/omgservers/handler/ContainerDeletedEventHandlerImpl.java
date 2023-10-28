package com.omgservers.handler;

import com.omgservers.model.dto.internal.GetContainerRequest;
import com.omgservers.model.dto.internal.GetContainerResponse;
import com.omgservers.model.dto.internal.StopContainerRequest;
import com.omgservers.model.dto.internal.StopContainerResponse;
import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ContainerDeletedEventBodyModel;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
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

        return getContainer(id)
                .flatMap(container -> stopContainer(id))
                .replaceWith(true);
    }

    Uni<ContainerModel> getContainer(final Long id) {
        final var request = new GetContainerRequest(id);
        return systemModule.getContainerService().getContainer(request)
                .map(GetContainerResponse::getContainer);
    }

    Uni<Boolean> stopContainer(final Long id) {
        final var request = new StopContainerRequest(id);
        return systemModule.getContainerService().stopContainer(request)
                .map(StopContainerResponse::getStopped);
    }
}
