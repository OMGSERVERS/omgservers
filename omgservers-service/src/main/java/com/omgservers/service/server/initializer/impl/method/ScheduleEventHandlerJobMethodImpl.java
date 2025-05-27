package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.configuration.JobQualifierEnum;
import com.omgservers.service.operation.server.ScheduleJobExecutionOperation;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleEventHandlerJobMethodImpl implements ScheduleEventHandlerJobMethod {
    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;

    @Override
    public void execute() {
        log.info("Schedule event handler job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.EVENT_HANDLER,
                scheduledExecution -> {
                    final var request = new ExecuteEventHandlerTaskRequest();
                    return taskService.execute(request)
                            // Event handler can never be unscheduled
                            .replaceWith(Boolean.FALSE);
                });

        log.info("Event handler job scheduled");
    }
}
