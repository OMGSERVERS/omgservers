package com.omgservers.service.module.system.impl.service.handlerService.impl.method;

import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.module.system.impl.operation.selectEvent.SelectEventOperation;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
public class HandleEventMethodImpl implements HandleEventMethod {

    final SelectEventOperation selectEventOperation;

    final Map<EventQualifierEnum, EventHandler> eventHandlers;
    final PgPool pgPool;

    public HandleEventMethodImpl(final SelectEventOperation selectEventOperation,
                                 final Instance<EventHandler> eventHandlerBeans,
                                 final PgPool pgPool) {
        this.selectEventOperation = selectEventOperation;
        eventHandlers = new ConcurrentHashMap<>();
        eventHandlerBeans.stream().forEach(eventHandler -> {
            final var qualifier = eventHandler.getQualifier();
            if (eventHandlers.put(qualifier, eventHandler) != null) {
                log.error("Multiple event handlers were detected, qualifier={}", qualifier);
            }
        });
        this.pgPool = pgPool;
    }

    @WithSpan
    @Override
    public Uni<HandleEventResponse> handleEvent(HandleEventRequest request) {
        final var event = request.getEvent();
        return handleEvent(event)
                .map(HandleEventResponse::new);
    }

    Uni<Boolean> handleEvent(EventModel event) {
        final var qualifier = event.getQualifier();
        if (eventHandlers.containsKey(qualifier)) {
            final var eventHandler = eventHandlers.get(qualifier);
            final var eventBody = event.getBody();
            if (qualifier.getBodyClass().isInstance(eventBody)) {
                return eventHandler.handle(event)
                        .replaceWith(true);
            } else {
                log.error("Event body has wrong type, event={}", event);
                return Uni.createFrom().item(true);
            }
        } else {
            return Uni.createFrom().item(true);
        }
    }
}
