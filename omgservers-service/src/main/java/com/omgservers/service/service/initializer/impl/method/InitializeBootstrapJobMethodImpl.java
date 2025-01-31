package com.omgservers.service.service.initializer.impl.method;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.task.TaskService;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskResponse;
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

    final GetServiceConfigOperation getServiceConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize bootstrap job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getServiceConfigOperation.getServiceConfig().initialization()
                            .bootstrapJob().interval();
                    final var trigger = scheduler.newJob("bootstrap")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteBootstrapTaskRequest();
                                return taskService.execute(request)
                                        .map(ExecuteBootstrapTaskResponse::getFinished)
                                        .flatMap(finished -> {
                                            if (finished) {
                                                scheduler.unscheduleJob("bootstrap");
                                                log.info("Bootstrap job was unscheduled after completion.");
                                            }

                                            return Uni.createFrom().voidItem();
                                        })
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.debug("Bootstrap job was scheduled, {}", trigger);
                });
    }
}
