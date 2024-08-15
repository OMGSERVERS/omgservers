package com.omgservers.service.service.bootstrap.impl.method.bootstrapSchedulerJob;

import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.task.TaskService;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapSchedulerJobMethodImpl implements BootstrapSchedulerJobMethod {

    final TaskService taskService;

    final GetConfigOperation getConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> bootstrapSchedulerJob() {
        log.debug("Bootstrap scheduler job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getConfigOperation.getServiceConfig().bootstrap().schedulerJob().interval();
                    final var trigger = scheduler.newJob("scheduler")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteSchedulerTaskRequest();
                                return taskService.executeSchedulerTask(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.info("Scheduler job was scheduled, {}", trigger);
                });
    }
}
