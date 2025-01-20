package com.omgservers.service.service.task.impl.method.executeSchedulerTask;

import com.omgservers.service.operation.job.test.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskResponse;
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

    final ExecuteTaskOperation executeTaskOperation;

    final SchedulerTaskImpl schedulerTask;

    @Override
    public Uni<ExecuteSchedulerTaskResponse> execute(final ExecuteSchedulerTaskRequest request) {
        log.trace("{}", request);

        return executeTaskOperation.execute(schedulerTask.execute())
                .map(ExecuteSchedulerTaskResponse::new);
    }
}
