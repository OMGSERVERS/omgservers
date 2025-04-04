package com.omgservers.service.service.task.impl.method.executeEventHandlerTask;

import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.HandleEventsRequest;
import com.omgservers.service.service.event.dto.HandleEventsResponse;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class EventHandlerTaskImpl {

    final EventService eventService;

    final Scheduler scheduler;

    public Uni<Boolean> execute() {
        return Multi.createBy().repeating()
                .uni(this::handleEvents)
                .whilst(Boolean.TRUE::equals)
                .collect().last()
                .repeat().withDelay(Duration.ofMillis(100)).indefinitely()
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }

    Uni<Boolean> handleEvents() {
        // TODO: make config option to change limit
        final var request = new HandleEventsRequest(16);
        return eventService.handleEvents(request)
                .map(HandleEventsResponse::getResult);
    }
}
