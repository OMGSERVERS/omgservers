package com.omgservers.worker.bootstrap;

import com.omgservers.worker.WorkerConfiguration;
import com.omgservers.worker.component.workerJobTask.WorkerJobTask;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapWorkerJob {

    static final String JOB_NAME = "workerJobTask";
    static final String JOB_INTERVAL = "1s";

    final WorkerJobTask workerJobTask;
    final Scheduler scheduler;

    @WithSpan
    void startUp(@Observes @Priority(WorkerConfiguration.START_UP_WORKER_JOB_PRIORITY) StartupEvent event) {
        final var trigger = scheduler.newJob(JOB_NAME)
                .setInterval(JOB_INTERVAL)
                .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                .setAsyncTask(scheduledExecution -> workerJobTask.executeTask()
                        .onFailure()
                        .recoverWithUni(t -> {
                            log.warn("Worker job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                            return Uni.createFrom().voidItem();
                        }))
                .schedule();
        log.info("Worker job was scheduled, {}", trigger);
    }


}
