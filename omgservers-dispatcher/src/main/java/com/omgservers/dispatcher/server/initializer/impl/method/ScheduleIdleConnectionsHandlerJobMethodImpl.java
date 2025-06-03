package com.omgservers.dispatcher.server.initializer.impl.method;

import com.omgservers.dispatcher.configuration.JobQualifierEnum;
import com.omgservers.dispatcher.operation.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.operation.ScheduleJobExecutionOperation;
import com.omgservers.dispatcher.server.task.TaskService;
import com.omgservers.dispatcher.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.quarkus.scheduler.Scheduler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleIdleConnectionsHandlerJobMethodImpl implements ScheduleIdleConnectionsHandlerJobMethod {

    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;
    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final Scheduler scheduler;

    @Override
    public void execute() {
        log.debug("Schedule idle connections handler job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.IDLE_CONNECTIONS_HANDLER,
                scheduledExecution -> {
                    final var request = new ExecuteIdleConnectionsHandlerTaskRequest();
                    return taskService.execute(request)
                            .map(ExecuteIdleConnectionsHandlerTaskResponse::getFinished);
                });

        log.debug("Idle connections handler job scheduled");
    }
}
