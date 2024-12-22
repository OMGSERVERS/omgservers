package com.omgservers.service.service.initializer.impl.method;

import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeBootstrapJobMethodImpl implements InitializeBootstrapJobMethod {

    final TaskService taskService;

    final GetConfigOperation getConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize bootstrap job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getConfigOperation.getServiceConfig().initialization()
                            .bootstrapJob().interval();
                    final var trigger = scheduler.newJob("bootstrap")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteBootstrapTaskRequest();
                                return taskService.execute(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.debug("Bootstrap job was scheduled, {}", trigger);
                });
    }
}
