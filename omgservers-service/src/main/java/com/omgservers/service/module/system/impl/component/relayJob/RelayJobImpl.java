package com.omgservers.service.module.system.impl.component.relayJob;

import com.omgservers.model.dto.system.RelayEventsRequest;
import com.omgservers.model.dto.system.RelayEventsResponse;
import com.omgservers.service.module.system.SystemModule;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RelayJobImpl implements RelayJob {

    final SystemModule systemModule;

    @Scheduled(every = "1s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    Uni<Void> relayJob() {
        return Multi.createBy().repeating()
                .uni(this::relayEvents)
                .whilst(Boolean.TRUE::equals)
                .collect().last()
                .replaceWithVoid();
    }

    Uni<Boolean> relayEvents() {
        final var request = new RelayEventsRequest(16);
        return systemModule.getEventService().relayEvents(request)
                .map(RelayEventsResponse::getResult);
    }
}
