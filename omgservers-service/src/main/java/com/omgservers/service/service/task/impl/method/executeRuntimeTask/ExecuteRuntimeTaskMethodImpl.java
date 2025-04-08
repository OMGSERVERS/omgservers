package com.omgservers.service.service.task.impl.method.executeRuntimeTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteRuntimeTaskMethodImpl implements ExecuteRuntimeTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final RuntimeTaskImpl runtimeTask;

    @Override
    public Uni<ExecuteRuntimeTaskResponse> execute(final ExecuteRuntimeTaskRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var taskArguments = new RuntimeTaskArguments(runtimeId);
        return executeTaskOperation.executeFailSafe(runtimeTask, taskArguments)
                .map(ExecuteRuntimeTaskResponse::new);
    }
}
