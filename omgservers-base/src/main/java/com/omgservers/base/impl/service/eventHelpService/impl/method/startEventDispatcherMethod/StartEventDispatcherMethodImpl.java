package com.omgservers.base.impl.service.eventHelpService.impl.method.startEventDispatcherMethod;

import com.omgservers.base.Dispatcher;
import com.omgservers.base.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.base.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.base.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import com.omgservers.base.impl.operation.selectEventOperation.SelectEventOperation;
import com.omgservers.base.impl.operation.updateEventStatusOperation.UpdateEventStatusOperation;
import com.omgservers.base.impl.operation.getConfigOperation.GetConfigOperation;
import com.omgservers.exception.ServerSideClientErrorException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StartEventDispatcherMethodImpl implements StartEventDispatcherMethod {
    static final String EVENT_DISPATCHER_JOB = "event-dispatcher";

    final HandlerHelpService handlerHelpService;

    final UpdateEventStatusOperation updateEventStatusOperation;
    final SelectEventOperation selectEventOperation;
    final GetConfigOperation getConfigOperation;

    final Dispatcher dispatcherInMemoryCache;

    final Scheduler scheduler;
    final PgPool pgPool;

    @Override
    public Uni<Void> startEventDispatcher() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var disableDispatcher = getConfigOperation.getConfig().disableDispatcher();
                    final var dispatcherCount = getConfigOperation.getConfig().dispatcherCount();
                    if (disableDispatcher) {
                        log.warn("Event dispatcher was disabled, skip method");
                    } else {
                        scheduleEventDispatcher(dispatcherCount);
                    }
                });
    }

    synchronized void scheduleEventDispatcher(final int dispatcherCount) {
        if (scheduler.getScheduledJob(EVENT_DISPATCHER_JOB) == null) {
            final var trigger = scheduler.newJob(EVENT_DISPATCHER_JOB)
                    .setInterval("1s")
                    .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                    .setAsyncTask(scheduledExecution -> dispatchTask(scheduledExecution, dispatcherCount))
                    .schedule();
            log.info("Event dispatcher was scheduled, count={}, {}", dispatcherCount, trigger);
        } else {
            log.error("Event dispatcher job was already scheduled, job={}", EVENT_DISPATCHER_JOB);
        }
    }

    Uni<Void> dispatchTask(final ScheduledExecution scheduledExecution, final int dispatcherCount) {
        return Multi.createFrom().range(0, dispatcherCount)
                .onItem().transformToUniAndMerge(this::dispatch)
                .collect().asList().replaceWithVoid();
    }

    @WithSpan
    Uni<Void> dispatch(int index) {
        return Uni.createFrom().item(dispatcherInMemoryCache::pollGroup)
                .flatMap(eventGroup -> {
                    if (eventGroup != null) {
                        final var eventId = eventGroup.getEventId();
                        return selectEvent(eventId)
                                .flatMap(event -> {
                                    final var request = new HandleEventHelpRequest(event);
                                    return handlerHelpService.handleEvent(request);
                                })
                                .map(HandleEventHelpResponse::getResult)
                                .invoke(result -> {
                                    if (result) {
                                        dispatcherInMemoryCache.returnGroup(eventGroup);
                                    } else {
                                        dispatcherInMemoryCache.postponeGroup(eventGroup);
                                    }
                                })
                                .flatMap(result -> updateStatus(eventId, result))
                                .onFailure(ServerSideClientErrorException.class)
                                .invoke(t -> dispatcherInMemoryCache.returnGroup(eventGroup))
                                .onFailure(ServerSideInternalException.class)
                                .invoke(t -> dispatcherInMemoryCache.postponeGroup(eventGroup));
                        //TODO: handle failures
                    } else {
                        return Uni.createFrom().voidItem()
                                .onItem().delayIt().by(Duration.ofSeconds(1));
                    }
                })
                .repeat().indefinitely()
                .collect().last().replaceWithVoid();
    }

    Uni<EventModel> selectEvent(final Long eventId) {
        return pgPool.withConnection(sqlConnection -> selectEventOperation
                .selectEvent(sqlConnection, eventId));
    }

    Uni<Void> updateStatus(final Long eventId, final Boolean processed) {
        return pgPool.withConnection(sqlConnection -> updateEventStatusOperation
                        .updateEventStatus(sqlConnection, eventId, processed ?
                                EventStatusEnum.PROCESSED : EventStatusEnum.FAILED))
                .replaceWithVoid();
    }
}
