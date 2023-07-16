package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.startEventDispatcherMethod;

import com.omgservers.application.module.internalModule.impl.operation.selectNewEventsOperation.SelectNewEventsOperation;
import com.omgservers.application.module.internalModule.impl.operation.updateEventStatusOperation.UpdateEventStatusOperation;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.EventInternalService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.ProducerHelpService;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventStatusEnum;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
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

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StartEventDispatcherMethodImpl implements StartEventDispatcherMethod {
    static final String EVENT_DISPATCHER_JOB = "event-dispatcher";

    final EventInternalService eventInternalService;

    final UpdateEventStatusOperation updateEventStatusOperation;
    final SelectNewEventsOperation selectNewEventsOperation;
    final GetConfigOperation getConfigOperation;

    final Scheduler scheduler;
    final PgPool pgPool;

    @Override
    public Uni<Void> startEventDispatcher() {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var disableDispatcher = getConfigOperation.getConfig().disableDispatcher();
                    final var dispatcherLimit = getConfigOperation.getConfig().dispatcherLimit();
                    if (disableDispatcher) {
                        log.warn("Event dispatcher was disabled, skip method");
                    } else {
                        scheduleEventDispatcher(dispatcherLimit);
                    }
                });
    }

    synchronized void scheduleEventDispatcher(final int dispatcherLimit) {
        if (scheduler.getScheduledJob(EVENT_DISPATCHER_JOB) == null) {
            final var trigger = scheduler.newJob(EVENT_DISPATCHER_JOB)
                    .setInterval("1s")
                    .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                    .setAsyncTask(scheduledExecution -> dispatchEventTask(scheduledExecution, dispatcherLimit))
                    .schedule();
            log.info("Event dispatcher was scheduled, limit={}, {}", dispatcherLimit, trigger);
        } else {
            log.error("Event dispatcher job was already scheduled, job={}", EVENT_DISPATCHER_JOB);
        }
    }

    @WithSpan
    Uni<Void> dispatchEventTask(final ScheduledExecution scheduledExecution,
                                final int dispatcherLimit) {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> pgPool.withConnection(sqlConnection -> selectNewEventsOperation
                                .selectNewEvents(sqlConnection, dispatcherLimit))
                        .flatMap(events -> Multi.createFrom().iterable(events)
                                .onItem().transformToUniAndConcatenate(this::dispatchEvent)
                                .collect().asList().replaceWithVoid()));
    }

    Uni<Void> dispatchEvent(final EventModel origin) {
        log.info("Dispatch event, {}", origin);
        final var event = EventCreatedEventBodyModel.createEvent(origin);
        final var request = new FireEventInternalRequest(event);
        return eventInternalService.fireEvent(request)
                .flatMap(voidItem -> pgPool.withConnection(sqlConnection -> updateEventStatusOperation
                        .updateEventStatus(sqlConnection, origin.getUuid(), EventStatusEnum.FIRED)))
                .replaceWithVoid();
    }
}
