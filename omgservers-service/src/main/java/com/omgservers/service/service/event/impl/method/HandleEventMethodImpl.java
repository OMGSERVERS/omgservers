package com.omgservers.service.service.event.impl.method;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.service.event.dto.HandleEventRequest;
import com.omgservers.service.service.event.dto.HandleEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
public class HandleEventMethodImpl implements HandleEventMethod {

    final Map<EventQualifierEnum, EventHandler> eventHandlers;

    public HandleEventMethodImpl(final Instance<EventHandler> eventHandlerBeans) {
        eventHandlers = new ConcurrentHashMap<>();
        eventHandlerBeans.stream().forEach(eventHandler -> {
            final var qualifier = eventHandler.getQualifier();
            if (eventHandlers.put(qualifier, eventHandler) != null) {
                log.error("Multiple event handlers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public Uni<HandleEventResponse> handleEvent(final HandleEventRequest request) {
        log.trace("Handle event, request={}", request);

        final var event = request.getEvent();
        return handleEvent(event)
                .map(HandleEventResponse::new);
    }

    Uni<Boolean> handleEvent(final EventModel event) {
        if (event.getStatus().equals(EventStatusEnum.PROCESSED)) {
            log.warn("Event was already processed, event={}", event);
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final var qualifier = event.getQualifier();
        if (eventHandlers.containsKey(qualifier)) {
            final var eventHandler = eventHandlers.get(qualifier);
            final var eventBody = event.getBody();
            if (qualifier.getBodyClass().isInstance(eventBody)) {
                return eventHandler.handle(event)
                        .replaceWith(Boolean.TRUE)
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.error("Event failed, " +
                                            "eventId={}, " +
                                            "qualifier={}, " +
                                            "{}:{}",
                                    event.getId(),
                                    qualifier,
                                    t.getClass().getSimpleName(),
                                    t.getMessage());

                            return Boolean.FALSE;
                        });
            } else {
                log.error("Event body has wrong type, event={}", event);
                return Uni.createFrom().item(Boolean.FALSE);
            }
        } else {
            log.trace("Handler wasn't found, event={}", event);
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}
