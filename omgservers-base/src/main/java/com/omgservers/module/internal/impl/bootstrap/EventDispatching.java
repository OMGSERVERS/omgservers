package com.omgservers.module.internal.impl.bootstrap;

import com.omgservers.Dispatcher;
import com.omgservers.dto.internal.HandleEventRequest;
import com.omgservers.dto.internal.HandleEventResponse;
import com.omgservers.exception.ServerSideClientErrorException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.internal.impl.operation.selectEvent.SelectEventOperation;
import com.omgservers.module.internal.impl.operation.updateEventStatus.UpdateEventStatusOperation;
import com.omgservers.module.internal.impl.service.handlerService.HandlerService;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EventDispatching {
    static final String EVENT_DISPATCHER_JOB = "event-dispatcher";

    final HandlerService handlerService;

    final ChangeWithContextOperation changeWithContextOperation;
    final UpdateEventStatusOperation updateEventStatusOperation;
    final SelectEventOperation selectEventOperation;
    final GetConfigOperation getConfigOperation;

    final Dispatcher dispatcherInMemoryCache;

    final Scheduler scheduler;
    final PgPool pgPool;

    @WithSpan
    void startup(@Observes @Priority(200) StartupEvent event) {
        log.info("Event dispatching bootstrap");

        final var disableDispatcher = getConfigOperation.getConfig().disableDispatcher();
        if (disableDispatcher) {
            log.warn("Event dispatcher was disabled, skip method");
        } else {
            final var dispatcherCount = getConfigOperation.getConfig().dispatcherCount();
            scheduleEventDispatcher(dispatcherCount);
        }
    }

    void scheduleEventDispatcher(final int dispatcherCount) {
        // TODO: reuse base job logic
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
                                    final var request = new HandleEventRequest(event);
                                    return handlerService.handleEvent(request);
                                })
                                .map(HandleEventResponse::getResult)
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
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        updateEventStatusOperation.updateEventStatus(changeContext, sqlConnection, eventId, processed ?
                                EventStatusEnum.PROCESSED : EventStatusEnum.FAILED))
                .replaceWithVoid();
    }
}
