package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.method;

import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.internalModule.impl.operation.selectEventOperation.SelectEventOperation;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
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
            } else {
                log.info("Event handler was added, qualifier={}, handler={}",
                        qualifier, eventHandler.getClass().getSimpleName());
            }
        });
        this.pgPool = pgPool;
    }

    @WithSpan
    @Override
    public Uni<HandleEventHelpResponse> handleEvent(HandleEventHelpRequest request) {
        final var event = request.getEvent();
        return handleEvent(event)
                .map(HandleEventHelpResponse::new);
    }

    Uni<Boolean> handleEvent(EventModel event) {
        final var qualifier = event.getQualifier();
        if (eventHandlers.containsKey(qualifier)) {
            final var eventHandler = eventHandlers.get(qualifier);
            final var eventBody = event.getBody();
            if (qualifier.getBodyClass().isInstance(eventBody)) {
                log.info("Handle event, {}", event);
                return eventHandler.handle(event)
                        .replaceWith(true);
            } else {
                log.error("Event body has wrong type, event={}", event);
                return Uni.createFrom().item(true);
            }
        } else {
            log.error("Event handler wasn't found, event={}", event);
            return Uni.createFrom().item(true);
        }
    }
}
