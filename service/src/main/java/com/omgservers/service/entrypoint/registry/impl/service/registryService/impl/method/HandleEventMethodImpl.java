package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.event.body.internal.DockerRegistryEventReceivedEventBodyModel;
import com.omgservers.registry.DockerRegistryEventDto;
import com.omgservers.registry.HandleEventsRegistryRequest;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleEventMethodImpl implements HandleEventMethod {

    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<Void> handleEvent(final HandleEventsRegistryRequest request) {
        log.debug("Handle events, request={}", request);

        final var events = request.getEvents();
        return Multi.createFrom().iterable(events)
                .onItem().transformToUniAndConcatenate(this::syncEvent)
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> syncEvent(final DockerRegistryEventDto event) {
        final var eventId = event.getId();
        final var eventBody = new DockerRegistryEventReceivedEventBodyModel(event);
        final var eventModel = eventModelFactory.create(eventBody, eventId);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
