package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.ServiceApplication;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
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

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    @WithSpan
    void startup(@Observes @Priority(ServiceApplication.START_UP_RELAY_JOB_PRIORITY) StartupEvent event) {
        final var disableRelay = getConfigOperation.getConfig().disableRelay();
        if (disableRelay) {
            log.warn("Relay job was disabled, skip operation");
        } else {
            final var request = new ScheduleJobRequest(0L, 0L, JobQualifierEnum.RELAY);
            systemModule.getJobService().scheduleJob(request)
                    .await().indefinitely();
        }
    }
}
