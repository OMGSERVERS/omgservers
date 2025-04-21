package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.configuration.JobQualifierEnum;
import com.omgservers.service.operation.server.ScheduleJobExecutionOperation;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleSchedulerJobMethodImpl implements ScheduleSchedulerJobMethod {

    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;

    @Override
    public void execute() {
        log.debug("Schedule scheduler job");

        scheduleJobExecutionOperation.execute(JobQualifierEnum.SCHEDULER,
                scheduledExecution -> {
                    final var request = new ExecuteSchedulerTaskRequest();
                    return taskService.execute(request)
                            .replaceWith(Boolean.FALSE);
                });

        log.info("Scheduler job scheduled");
    }
}
