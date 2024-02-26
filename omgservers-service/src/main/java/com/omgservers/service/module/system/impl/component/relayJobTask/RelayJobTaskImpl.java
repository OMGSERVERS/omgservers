package com.omgservers.service.module.system.impl.component.relayJobTask;

import com.omgservers.model.dto.system.RelayEventsRequest;
import com.omgservers.model.dto.system.RelayEventsResponse;
import com.omgservers.service.module.system.SystemModule;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RelayJobTaskImpl implements RelayJobTask {

    final SystemModule systemModule;
    final Scheduler scheduler;

    @Override
    public Uni<Void> executeTask() {
        log.info("Relay job was executed");
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
