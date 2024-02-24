package com.omgservers.service.module.system.impl.component.eventHandler;

import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import com.omgservers.service.module.system.SystemModule;
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

    final SystemModule systemModule;

    @Incoming("inbox-events")
    @Blocking(ordered = false)
    Uni<Void> eventHandler(Long message) {
        final var eventId = message;
        return handleEvent(eventId)
                .replaceWithVoid();
    }

    Uni<Boolean> handleEvent(final Long eventId) {
        final var request = new HandleEventRequest(eventId);
        return systemModule.getEventService().handleEvent(request)
                .map(HandleEventResponse::getHandled);
    }
}
