package com.omgservers.service.service.task.impl.method.executeSchedulerTask;

import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteSchedulerTaskResponse;
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
    public Uni<ExecuteSchedulerTaskResponse> execute(final ExecuteSchedulerTaskRequest request) {
        log.trace("Requested, {}", request);

        return schedulerTask.execute()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .invoke(result -> log.debug("Task finished, result={}", result))
                .map(ExecuteSchedulerTaskResponse::new);
    }
}
