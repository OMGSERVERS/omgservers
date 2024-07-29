package com.omgservers.service.server.service.event.impl.method.handleEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.service.system.HandleEventRequest;
import com.omgservers.schema.service.system.HandleEventResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.EventStatusEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.server.service.event.component.eventEmitter.EventEmitter;
import com.omgservers.service.server.service.event.operation.deleteEventAndUpdateStatus.DeleteEventAndUpdateStatusOperation;
import com.omgservers.service.server.service.event.operation.selectEvent.SelectEventOperation;
import com.omgservers.service.server.service.event.component.eventEmitter.EventEmitter;
import com.omgservers.service.server.service.event.operation.deleteEventAndUpdateStatus.DeleteEventAndUpdateStatusOperation;
import com.omgservers.service.server.service.event.operation.selectEvent.SelectEventOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
public class HandleEventMethodImpl implements HandleEventMethod {

    final DeleteEventAndUpdateStatusOperation deleteEventAndUpdateStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final SelectEventOperation selectEventOperation;

    final Map<EventQualifierEnum, EventHandler> eventHandlers;
    final EventEmitter eventEmitter;
    final ObjectMapper objectMapper;
    final PgPool pgPool;

    public HandleEventMethodImpl(final DeleteEventAndUpdateStatusOperation deleteEventAndUpdateStatusOperation,
                                 final ChangeWithContextOperation changeWithContextOperation,
                                 final SelectEventOperation selectEventOperation,
                                 final Instance<EventHandler> eventHandlerBeans,
                                 final ObjectMapper objectMapper,
                                 final EventEmitter eventEmitter,
                                 final PgPool pgPool) {
        this.deleteEventAndUpdateStatusOperation = deleteEventAndUpdateStatusOperation;
        this.changeWithContextOperation = changeWithContextOperation;
        this.selectEventOperation = selectEventOperation;
        eventHandlers = new ConcurrentHashMap<>();
        eventHandlerBeans.stream().forEach(eventHandler -> {
            final var qualifier = eventHandler.getQualifier();
            if (eventHandlers.put(qualifier, eventHandler) != null) {
                log.error("Multiple event handlers were detected, qualifier={}", qualifier);
            }
        });
        this.eventEmitter = eventEmitter;
        this.objectMapper = objectMapper;
        this.pgPool = pgPool;
    }

    @Override
    public Uni<HandleEventResponse> handleEvent(final HandleEventRequest request) {
        log.trace("Handle event, request={}", request);

        final var eventId = request.getEventId();
        return pgPool.withConnection(sqlConnection -> selectEventOperation.selectEvent(sqlConnection, eventId))
                .flatMap(event -> handleEvent(event)
                        .call(handled -> forwardEvent(event))
                        .flatMap(handled -> {
                            final var status = handled ? EventStatusEnum.HANDLED : EventStatusEnum.FAILED;
                            return changeWithContextOperation.<Boolean>changeWithContext(
                                            (changeContext, sqlConnection) -> deleteEventAndUpdateStatusOperation
                                                    .deleteEventAndUpdateStatus(
                                                            changeContext,
                                                            sqlConnection,
                                                            eventId,
                                                            status))
                                    .map(ChangeContext::getResult);
                        })
                )
                .map(HandleEventResponse::new);
    }

    Uni<Boolean> handleEvent(final EventModel event) {
        if (event.getStatus().equals(EventStatusEnum.HANDLED)) {
            log.warn("Event was already handled, event={}", event);
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final var qualifier = event.getQualifier();
        if (eventHandlers.containsKey(qualifier)) {
            final var eventHandler = eventHandlers.get(qualifier);
            final var eventBody = event.getBody();
            if (qualifier.getBodyClass().isInstance(eventBody)) {
                return eventHandler.handle(event)
                        .replaceWith(Boolean.TRUE)
                        .onFailure(ServerSideClientException.class)
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
            log.debug("Handler wasn't found, event={}", event);
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }

    Uni<Boolean> forwardEvent(final EventModel event) {
        if (event.getQualifier().isForward()) {
            try {
                final var eventMessage = objectMapper.writeValueAsString(event);
                return eventEmitter.forwardEvent(eventMessage)
                        .replaceWith(Boolean.TRUE);
            } catch (IOException e) {
                throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
            }
        } else {
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}
