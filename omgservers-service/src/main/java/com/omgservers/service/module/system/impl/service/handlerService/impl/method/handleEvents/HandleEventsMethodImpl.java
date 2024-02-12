package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents;

import com.omgservers.model.dto.system.HandleEventsRequest;
import com.omgservers.model.dto.system.HandleEventsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideClientExceptionException;
import com.omgservers.service.module.system.impl.operation.deleteEventsByIds.DeleteEventsByIdsOperation;
import com.omgservers.service.module.system.impl.operation.selectEventsForUpdate.SelectEventsForUpdateOperation;
import com.omgservers.service.module.system.impl.operation.updateEventsAvailableByIds.UpdateEventsAvailableByIdsOperation;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.EventHandler;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
public class HandleEventsMethodImpl implements HandleEventsMethod {

    final UpdateEventsAvailableByIdsOperation updateEventsAvailableByIdsOperation;
    final SelectEventsForUpdateOperation selectEventsForUpdateOperation;
    final DeleteEventsByIdsOperation deleteEventsByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final Map<EventQualifierEnum, EventHandler> eventHandlers;
    final PgPool pgPool;

    public HandleEventsMethodImpl(final UpdateEventsAvailableByIdsOperation updateEventsAvailableByIdsOperation,
                                  final SelectEventsForUpdateOperation selectEventsForUpdateOperation,
                                  final DeleteEventsByIdsOperation deleteEventsByIdsOperation,
                                  final ChangeWithContextOperation changeWithContextOperation,
                                  final Instance<EventHandler> eventHandlerBeans,
                                  final PgPool pgPool) {
        this.updateEventsAvailableByIdsOperation = updateEventsAvailableByIdsOperation;
        this.selectEventsForUpdateOperation = selectEventsForUpdateOperation;
        this.deleteEventsByIdsOperation = deleteEventsByIdsOperation;
        this.changeWithContextOperation = changeWithContextOperation;
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
    public Uni<HandleEventsResponse> handleEvents(final HandleEventsRequest request) {
        log.trace("Handle events, request={}", request);

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) -> {
                    final var limit = request.getLimit();
                    return selectEventsForUpdateOperation.selectEventsForUpdate(sqlConnection, limit)
                            .flatMap(events -> {
                                if (events.isEmpty()) {
                                    return Uni.createFrom().item(false);
                                } else {
                                    return Multi.createFrom().iterable(events)
                                            .onItem().transformToUniAndConcatenate(this::handleEvent)
                                            .collect().asList()
                                            .flatMap(results -> {
                                                final var processed = results.stream()
                                                        .filter(tuple -> tuple.getItem2().equals(Boolean.TRUE))
                                                        .map(tuple -> tuple.getItem1().getId())
                                                        .toList();

                                                final var postponed = results.stream()
                                                        .filter(tuple -> tuple.getItem2().equals(Boolean.FALSE))
                                                        .map(tuple -> tuple.getItem1().getId())
                                                        .toList();

                                                log.debug("Events were handled, limit={}, processed={}, postponed={}",
                                                        limit,
                                                        processed.size(),
                                                        postponed.size());

                                                var uni = Uni.createFrom().voidItem();

                                                if (processed.size() > 0) {
                                                    uni = uni.flatMap(voidItem -> deleteEventsByIdsOperation
                                                            .deleteEventsByIds(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    processed)
                                                            .replaceWithVoid());
                                                }

                                                if (postponed.size() > 0) {
                                                    uni = uni.flatMap(voidItem -> updateEventsAvailableByIdsOperation
                                                            .updateEventsAvailableByIds(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    postponed)
                                                            .replaceWithVoid());
                                                }

                                                return uni;
                                            })
                                            .replaceWith(true);
                                }
                            });
                })
                .map(ChangeContext::getResult)
                .map(HandleEventsResponse::new);
    }

    Uni<Tuple2<EventModel, Boolean>> handleEvent(final EventModel event) {
        final var qualifier = event.getQualifier();
        if (eventHandlers.containsKey(qualifier)) {
            final var eventHandler = eventHandlers.get(qualifier);
            final var eventBody = event.getBody();
            if (qualifier.getBodyClass().isInstance(eventBody)) {
                return eventHandler.handle(event)
                        .replaceWith(Tuple2.of(event, Boolean.TRUE))
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

                            // Ack
                            return Tuple2.of(event, Boolean.TRUE);
                        })
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.error("Handling failed, " +
                                            "eventId={}, " +
                                            "qualifier={}, " +
                                            "{}:{}",
                                    event.getId(),
                                    qualifier,
                                    t.getClass().getSimpleName(),
                                    t.getMessage());

                            // Nack
                            return Tuple2.of(event, Boolean.FALSE);
                        });
            } else {
                log.error("Event body has wrong type, event={}", event);
                return Uni.createFrom().item(Tuple2.of(event, Boolean.TRUE));
            }
        } else {
            log.warn("Handler wasn't found, event={}", event);
            return Uni.createFrom().item(Tuple2.of(event, Boolean.TRUE));
        }
    }
}
