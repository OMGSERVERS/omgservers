package com.omgservers.connector.server.initializer.impl.method;

import com.omgservers.connector.configuration.JobQualifierEnum;
import com.omgservers.connector.operation.ScheduleJobExecutionOperation;
import com.omgservers.connector.server.task.TaskService;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleTokenRefresherJobMethodImpl implements ScheduleTokenRefresherJobMethod {

    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;

    @Override
    public void execute() {
        log.info("Schedule token refresher job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.TOKEN_REFRESHER,
                scheduledExecution -> {
                    final var request = new ExecuteTokenRefresherTaskRequest();
                    return taskService.execute(request)
                            .map(ExecuteTokenRefresherTaskResponse::getFinished);
                });

        log.info("Token refresher job scheduled");
    }
}
