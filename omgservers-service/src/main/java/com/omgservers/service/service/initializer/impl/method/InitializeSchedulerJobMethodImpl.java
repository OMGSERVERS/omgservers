package com.omgservers.service.service.initializer.impl.method;

import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskRequest;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeSchedulerJobMethodImpl implements InitializeSchedulerJobMethod {

    final TaskService taskService;

    final GetServiceConfigOperation getServiceConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize scheduler job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getServiceConfigOperation.getServiceConfig().initialization()
                            .schedulerJob().interval();
                    final var trigger = scheduler.newJob("scheduler")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteSchedulerTaskRequest();
                                return taskService.execute(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.debug("Scheduler job was scheduled, {}", trigger);
                });
    }
}
