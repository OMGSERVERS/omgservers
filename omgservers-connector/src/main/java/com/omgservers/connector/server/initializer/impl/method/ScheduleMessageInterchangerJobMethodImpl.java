package com.omgservers.connector.server.initializer.impl.method;

import com.omgservers.connector.configuration.JobQualifierEnum;
import com.omgservers.connector.operation.ScheduleJobExecutionOperation;
import com.omgservers.connector.server.task.TaskService;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleMessageInterchangerJobMethodImpl implements ScheduleMessageInterchangerJobMethod {

    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;

    @Override
    public void execute() {
        log.info("Schedule message interchanger job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.MESSAGE_INTERCHANGER,
                scheduledExecution -> {
                    final var request = new ExecuteMessageInterchangerTaskRequest();
                    return taskService.execute(request)
                            .map(ExecuteMessageInterchangerTaskResponse::getFinished);
                });

        log.info("Message interchanger job scheduled");
    }
}
