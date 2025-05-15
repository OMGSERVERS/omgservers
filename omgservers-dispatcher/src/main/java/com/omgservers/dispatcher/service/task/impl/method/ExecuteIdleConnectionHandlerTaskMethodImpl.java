package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.operation.ExecuteTaskOperation;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteIdleConnectionHandlerTaskMethodImpl implements ExecuteIdleConnectionHandlerTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final IdleConnectionsHandlerTaskImpl idleConnectionsHandlerTask;

    @Override
    public Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(final ExecuteIdleConnectionsHandlerTaskRequest request) {
        log.debug("{}", request);

        final var taskArguments = new IdleConnectionsHandlerTaskArguments();
        return executeTaskOperation.executeFailSafe(idleConnectionsHandlerTask, taskArguments)
                .map(ExecuteIdleConnectionsHandlerTaskResponse::new);
    }
}
