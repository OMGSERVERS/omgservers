package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.module.system.impl.component.relayJobTask.RelayJobTask;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapRelayJob {

    final GetConfigOperation getConfigOperation;

    final Scheduler scheduler;
    final RelayJobTask relayJobTask;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_RELAY_JOB_PRIORITY) StartupEvent event) {
        final var disableRelayJob = getConfigOperation.getServiceConfig().disableRelayJob();
        if (disableRelayJob) {
            log.warn("Relay job was disabled, skip operation");
        } else {
            final var trigger = scheduler.newJob("relayJobTask")
                    .setInterval("1s")
                    .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                    .setAsyncTask(scheduledExecution -> relayJobTask.executeTask())
                    .schedule();
            log.info("Relay job was scheduled, {}", trigger);
        }
    }
}
