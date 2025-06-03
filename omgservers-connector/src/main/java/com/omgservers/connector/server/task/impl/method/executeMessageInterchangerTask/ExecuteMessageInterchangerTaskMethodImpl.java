package com.omgservers.connector.server.task.impl.method.executeMessageInterchangerTask;

import com.omgservers.connector.operation.ExecuteTaskOperation;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteMessageInterchangerTaskMethodImpl implements ExecuteMessageInterchangerTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final MessageInterchangerTaskImpl idleConnectionsHandlerTask;

    @Override
    public Uni<ExecuteMessageInterchangerTaskResponse> execute(final ExecuteMessageInterchangerTaskRequest request) {
        log.debug("{}", request);

        final var taskArguments = new MessageInterchangerTaskArguments();
        return executeTaskOperation.executeFailSafe(idleConnectionsHandlerTask, taskArguments)
                .map(ExecuteMessageInterchangerTaskResponse::new);
    }
}
