package com.omgservers.connector.server.initializer.impl.method;

import com.omgservers.connector.configuration.JobQualifierEnum;
import com.omgservers.connector.operation.ScheduleJobExecutionOperation;
import com.omgservers.connector.server.task.TaskService;
import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleIdleConnectionsHandlerJobMethodImpl implements ScheduleIdleConnectionsHandlerJobMethod {

    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;

    @Override
    public void execute() {
        log.info("Schedule idle connections handler job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.IDLE_CONNECTIONS_HANDLER,
                scheduledExecution -> {
                    final var request = new ExecuteIdleConnectionsHandlerTaskRequest();
                    return taskService.execute(request)
                            .map(ExecuteIdleConnectionsHandlerTaskResponse::getFinished);
                });

        log.info("Idle connections handler job scheduled");
    }
}
