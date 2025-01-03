package com.omgservers.dispatcher.initializer.impl.method;

import com.omgservers.dispatcher.operation.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.service.task.TaskService;
import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskRequest;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeExpiredConnectionsHandlerJobMethodImpl implements InitializeExpiredConnectionsHandlerJobMethod {

    final TaskService taskService;

    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize expired connections handler job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getDispatcherConfigOperation.getDispatcherConfig()
                            .expiredConnectionsHandlerJobInterval();
                    final var trigger = scheduler.newJob("expired-connections-handler")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteExpiredConnectionsHandlerTaskRequest();
                                return taskService.execute(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.info("Expired connection handler job was scheduled, {}", trigger);
                });
    }
}
