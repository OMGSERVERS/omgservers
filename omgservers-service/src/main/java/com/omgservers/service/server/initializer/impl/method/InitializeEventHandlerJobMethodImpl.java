package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.configuration.JobQualifierEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.operation.server.ScheduleJobExecutionOperation;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeEventHandlerJobMethodImpl implements InitializeEventHandlerJobMethod {
    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute() {
        log.debug("Initialize job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.EVENT_HANDLER,
                scheduledExecution -> {
                    final var request = new ExecuteEventHandlerTaskRequest();
                    return taskService.execute(request)
                            .replaceWith(Boolean.FALSE);
                });
    }
}
