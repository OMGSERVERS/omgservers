package com.omgservers.service.service.task.impl.method.executeBootstrapTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteBootstrapTaskMethodImpl implements ExecuteBootstrapTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final BootstrapTaskImpl bootstrapTask;

    @Override
    public Uni<ExecuteBootstrapTaskResponse> execute(final ExecuteBootstrapTaskRequest request) {
        log.trace("{}", request);

        final var taskArguments = new BootstrapTaskArguments();
        return executeTaskOperation.executeFailSafe(bootstrapTask, taskArguments)
                .map(ExecuteBootstrapTaskResponse::new);
    }
}
