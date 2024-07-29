package com.omgservers.service.server.service.task.impl.method.executeRelayTask;

import com.omgservers.schema.service.system.RelayEventsRequest;
import com.omgservers.schema.service.system.RelayEventsResponse;
import com.omgservers.service.server.service.event.EventService;
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
public class RelayTaskImpl {

    final EventService eventService;

    final Scheduler scheduler;

    public Uni<Boolean> executeTask() {
        return Multi.createBy().repeating()
                .uni(this::relayEvents)
                .whilst(Boolean.TRUE::equals)
                .collect().last()
                .repeat().withDelay(Duration.ofMillis(100)).indefinitely()
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }

    Uni<Boolean> relayEvents() {
        final var request = new RelayEventsRequest(16);
        return eventService.relayEvents(request)
                .map(RelayEventsResponse::getResult);
    }
}
