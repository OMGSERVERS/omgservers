package com.omgservers.service.module.system.impl.service.eventService.impl.method.handleEvent;

import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.service.exception.ServerSideClientExceptionException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.impl.operation.deleteEventAndUpdateStatus.DeleteEventAndUpdateStatusOperation;
import com.omgservers.service.module.system.impl.operation.selectEvent.SelectEventOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
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

    final DeleteEventAndUpdateStatusOperation deleteEventAndUpdateStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final SelectEventOperation selectEventOperation;

    final Map<EventQualifierEnum, EventHandler> eventHandlers;
    final PgPool pgPool;

    public HandleEventMethodImpl(final DeleteEventAndUpdateStatusOperation deleteEventAndUpdateStatusOperation,
                                 final ChangeWithContextOperation changeWithContextOperation,
                                 final SelectEventOperation selectEventOperation,
                                 final Instance<EventHandler> eventHandlerBeans,
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
        this.pgPool = pgPool;
    }

    @Override
    public Uni<HandleEventResponse> handleEvent(final HandleEventRequest request) {
        log.trace("Handle event, request={}", request);

        final var eventId = request.getEventId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        selectEventOperation.selectEvent(sqlConnection, eventId)
                                .flatMap(this::handleEvent)
                                .flatMap(handled -> {
                                    final var status = handled ? EventStatusEnum.HANDLED : EventStatusEnum.FAILED;
                                    return deleteEventAndUpdateStatusOperation.deleteEventAndUpdateStatus(
                                            changeContext,
                                            sqlConnection,
                                            eventId,
                                            status);
                                }))
                .map(ChangeContext::getResult)
                .map(HandleEventResponse::new);
    }

    Uni<Boolean> handleEvent(final EventModel event) {
        final var qualifier = event.getQualifier();
        if (eventHandlers.containsKey(qualifier)) {
            final var eventHandler = eventHandlers.get(qualifier);
            final var eventBody = event.getBody();
            if (qualifier.getBodyClass().isInstance(eventBody)) {
                return eventHandler.handle(event)
                        .replaceWith(Boolean.TRUE)
                        .onFailure(ServerSideClientExceptionException.class)
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
            log.info("Handler wasn't found, event={}", event);
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}
