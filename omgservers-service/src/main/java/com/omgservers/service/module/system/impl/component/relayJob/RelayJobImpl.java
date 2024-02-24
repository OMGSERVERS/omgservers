package com.omgservers.service.module.system.impl.component.relayJob;

import com.omgservers.model.dto.system.RelayEventsRequest;
import com.omgservers.model.dto.system.RelayEventsResponse;
import com.omgservers.service.ServiceConfiguration;
import com.omgservers.service.module.system.SystemModule;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RelayJobImpl implements RelayJob, Scheduled.SkipPredicate {

    final SystemModule systemModule;
    final AtomicBoolean skipExecution;

    public RelayJobImpl(final SystemModule systemModule) {
        this.systemModule = systemModule;
        this.skipExecution = new AtomicBoolean(true);
    }

    @WithSpan
    void startup(@Observes @Priority(ServiceConfiguration.START_UP_BOOTSTRAP_RELAY_JOB) final StartupEvent event) {
        skipExecution.set(false);
        log.info("Relay job was activated");
    }

    @Override
    public boolean test(ScheduledExecution execution) {
        return skipExecution.get();
    }

    @Scheduled(every = "1s",
            concurrentExecution = Scheduled.ConcurrentExecution.SKIP,
            skipExecutionIf = RelayJobImpl.class)
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
