package com.omgservers.service.entrypoint.registry.impl.service.registryService.impl.method.handleEvents;

import com.omgservers.schema.entrypoint.registry.handleEvents.DockerRegistryEventDto;
import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import com.omgservers.service.event.body.internal.DockerRegistryEventReceivedEventBodyModel;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.service.event.EventService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleEventsMethodImpl implements HandleEventsMethod {

    final EventService eventService;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<Void> handleEvents(final HandleEventsRegistryRequest request) {
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
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}