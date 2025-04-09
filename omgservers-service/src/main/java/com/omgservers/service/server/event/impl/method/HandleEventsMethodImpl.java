package com.omgservers.service.server.event.impl.method;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.server.event.EventService;
import com.omgservers.service.server.event.dto.HandleEventRequest;
import com.omgservers.service.server.event.dto.HandleEventResponse;
import com.omgservers.service.server.event.dto.HandleEventsRequest;
import com.omgservers.service.server.event.dto.HandleEventsResponse;
import com.omgservers.service.server.event.operation.DeleteEventsAndUpdateStatusOperation;
import com.omgservers.service.server.event.operation.SelectActiveEventsOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HandleEventsMethodImpl implements HandleEventsMethod {

    final EventService eventService;

    final DeleteEventsAndUpdateStatusOperation deleteEventsAndUpdateStatusOperation;
    final SelectActiveEventsOperation selectActiveEventsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<HandleEventsResponse> execute(final HandleEventsRequest request) {
        log.trace("Handle events, request={}", request);

        return pgPool.withTransaction(sqlConnection -> {
                    final var limit = request.getLimit();
                    return selectActiveEventsOperation.execute(sqlConnection, limit);
                })
                .flatMap(events -> {
                    if (events.isEmpty()) {
                        return Uni.createFrom().item(Boolean.FALSE);
                    } else {
                        return Multi.createFrom().iterable(events)
                                .onItem().transformToUniAndMerge(event -> {
                                    final var handleEventRequest = new HandleEventRequest(event);
                                    return eventService.handleEvent(handleEventRequest)
                                            .map(HandleEventResponse::getProcessed)
                                            .invoke(processed -> {
                                                if (processed) {
                                                    event.setStatus(EventStatusEnum.PROCESSED);
                                                } else {
                                                    event.setStatus(EventStatusEnum.FAILED);
                                                }
                                            })
                                            .replaceWith(event);
                                })
                                .collect().asList()
                                .flatMap(results -> {
                                    final var processed = results.stream()
                                            .filter(event -> event.getStatus().equals(EventStatusEnum.PROCESSED))
                                            .map(EventModel::getId)
                                            .toList();

                                    final var failed = results.stream()
                                            .filter(event -> event.getStatus().equals(EventStatusEnum.FAILED))
                                            .map(EventModel::getId)
                                            .toList();

                                    final var finalResult = !processed.isEmpty() || !failed.isEmpty();

                                    return Uni.combine().all()
                                            .unis(deleteEventsAndUpdateStatus(processed, EventStatusEnum.PROCESSED),
                                                    deleteEventsAndUpdateStatus(failed, EventStatusEnum.FAILED))
                                            .asTuple()
                                            .replaceWith(finalResult);
                                });
                    }
                })
                .map(HandleEventsResponse::new);
    }

    Uni<Void> deleteEventsAndUpdateStatus(final List<Long> ids, final EventStatusEnum status) {
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteEventsAndUpdateStatusOperation
                                .execute(changeContext, sqlConnection, ids, status))
                .replaceWithVoid();
    }
}
