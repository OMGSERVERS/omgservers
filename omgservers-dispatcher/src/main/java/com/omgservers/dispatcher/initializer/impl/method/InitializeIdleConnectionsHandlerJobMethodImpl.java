package com.omgservers.dispatcher.initializer.impl.method;

import com.omgservers.dispatcher.operation.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.service.task.TaskService;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeIdleConnectionsHandlerJobMethodImpl implements InitializeIdleConnectionsHandlerJobMethod {

    final TaskService taskService;

    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize idle connections handler job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getDispatcherConfigOperation.getDispatcherConfig()
                            .idleConnectionsHandlerJobInterval();
                    final var trigger = scheduler.newJob("idle-connections-handler")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteIdleConnectionsHandlerTaskRequest();
                                return taskService.execute(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.info("Idle connection handler job was scheduled, {}", trigger);
                });
    }
}
