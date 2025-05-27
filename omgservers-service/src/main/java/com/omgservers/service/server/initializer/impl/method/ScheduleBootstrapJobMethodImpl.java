package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.configuration.JobQualifierEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.operation.server.ScheduleJobExecutionOperation;
import com.omgservers.service.server.task.TaskResult;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ScheduleBootstrapJobMethodImpl implements ScheduleBootstrapJobMethod {
    final TaskService taskService;

    final ScheduleJobExecutionOperation scheduleJobExecutionOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute() {
        final var masterUri = getServiceConfigOperation.getServiceConfig().master().uri();
        final var thisUri = getServiceConfigOperation.getServiceConfig().shard().uri();
        if (masterUri.equals(thisUri)) {
            log.info("Schedule bootstrap job");

            scheduleJobExecutionOperation.execute(JobQualifierEnum.BOOTSTRAP,
                    scheduledExecution -> {
                        final var request = new ExecuteBootstrapTaskRequest();
                        return taskService.execute(request)

                                .map(ExecuteBootstrapTaskResponse::getTaskResult)
                                .map(taskResult -> {
                                    if (taskResult.equals(TaskResult.DONE)) {
                                        // TRUE means job can be unscheduled
                                        return Boolean.TRUE;
                                    } else {
                                        return Boolean.FALSE;
                                    }
                                });
                    });

            log.info("Bootstrap job scheduled");
        } else {
            log.info("Bootstrap job is being scheduled on master, skip operation");
        }
    }
}
