package com.omgservers.dispatcher.initializer.impl.method;

import com.omgservers.dispatcher.operation.getDispatcherConfig.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.service.task.TaskService;
import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskRequest;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeDispatcherJobMethodImpl implements InitializeDispatcherJobMethod {

    final TaskService taskService;

    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize dispatcher job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getDispatcherConfigOperation.getDispatcherConfig()
                            .jobInterval();
                    final var trigger = scheduler.newJob("dispatcher")
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                final var request = new ExecuteDispatcherTaskRequest();
                                return taskService.execute(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.info("Dispatcher job was scheduled, {}", trigger);
                });
    }
}
