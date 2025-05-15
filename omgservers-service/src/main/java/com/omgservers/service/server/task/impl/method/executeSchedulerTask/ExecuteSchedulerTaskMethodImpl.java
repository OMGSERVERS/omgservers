package com.omgservers.service.server.task.impl.method.executeSchedulerTask;

import com.omgservers.service.operation.task.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteSchedulerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteSchedulerTaskMethodImpl implements ExecuteSchedulerTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final SchedulerTaskImpl schedulerTask;

    @Override
    public Uni<ExecuteSchedulerTaskResponse> execute(final ExecuteSchedulerTaskRequest request) {
        log.debug("{}", request);

        final var taskArguments = new SchedulerTaskArguments();
        return executeTaskOperation.executeFailSafe(schedulerTask, taskArguments)
                .map(ExecuteSchedulerTaskResponse::new);
    }
}
