package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.operation.server.PutIntoMdcOperation;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskRequest;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeEventHandlerJobMethodImpl implements InitializeEventHandlerJobMethod {
    private static final String JOB_QUALIFIER = "event-handler";

    final TaskService taskService;

    final GetServiceConfigOperation getServiceConfigOperation;
    final PutIntoMdcOperation putIntoMdcOperation;

    final Scheduler scheduler;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize event handler job");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var interval = getServiceConfigOperation.getServiceConfig().initialization()
                            .eventHandlerJob().interval();
                    final var trigger = scheduler.newJob(JOB_QUALIFIER)
                            .setInterval(interval)
                            .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                            .setAsyncTask(scheduledExecution -> {
                                putIntoMdcOperation.putArbitrarySubject(JOB_QUALIFIER);

                                final var request = new ExecuteEventHandlerTaskRequest();
                                return taskService.execute(request)
                                        .replaceWithVoid();
                            })
                            .schedule();

                    log.debug("Event handler job was scheduled, {}", trigger);
                });
    }
}
