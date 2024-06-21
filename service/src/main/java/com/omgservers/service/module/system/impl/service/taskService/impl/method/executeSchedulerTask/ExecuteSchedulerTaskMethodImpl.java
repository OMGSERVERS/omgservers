package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeSchedulerTask;

import com.omgservers.model.dto.system.task.ExecuteSchedulerTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteSchedulerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteSchedulerTaskMethodImpl implements ExecuteSchedulerTaskMethod {

    final SchedulerTaskImpl schedulerTask;

    @Override
    public Uni<ExecuteSchedulerTaskResponse> executeSchedulerTask(final ExecuteSchedulerTaskRequest request) {
        log.debug("Execute scheduler task, request={}", request);

        return schedulerTask.executeTask()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteSchedulerTaskResponse::new);
    }
}
