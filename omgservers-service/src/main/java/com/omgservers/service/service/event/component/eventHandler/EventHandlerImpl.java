package com.omgservers.service.service.event.component.eventHandler;

import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.HandleEventRequest;
import com.omgservers.service.service.event.dto.HandleEventResponse;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EventHandlerImpl implements EventHandler {

    final EventService eventService;

    @Incoming("incoming-events")
    @Blocking(ordered = false)
    Uni<Void> eventHandler(Long message) {
        log.trace("Message was received, {}", message);

        final var eventId = message;
        return handleEvent(eventId)
                .replaceWithVoid()
                .onFailure()
                .invoke(t -> log.error("Event handling error, eventId={}, {}:{}", eventId,
                        t.getClass().getSimpleName(),
                        t.getMessage()));
    }

    Uni<Boolean> handleEvent(final Long eventId) {
        final var request = new HandleEventRequest(eventId);
        return eventService.handleEvent(request)
                .map(HandleEventResponse::getHandled);
    }
}
