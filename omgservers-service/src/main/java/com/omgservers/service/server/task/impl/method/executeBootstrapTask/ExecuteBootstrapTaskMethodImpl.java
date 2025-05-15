package com.omgservers.service.server.task.impl.method.executeBootstrapTask;

import com.omgservers.service.operation.task.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskResponse;
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
        log.debug("{}", request);

        final var taskArguments = new BootstrapTaskArguments();
        return executeTaskOperation.executeFailSafe(bootstrapTask, taskArguments)
                .map(ExecuteBootstrapTaskResponse::new);
    }
}
