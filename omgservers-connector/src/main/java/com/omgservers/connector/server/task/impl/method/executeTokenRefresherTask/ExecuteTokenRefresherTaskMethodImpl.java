package com.omgservers.connector.server.task.impl.method.executeTokenRefresherTask;

import com.omgservers.connector.operation.ExecuteTaskOperation;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteTokenRefresherTaskMethodImpl implements ExecuteTokenRefresherTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final TokenRefresherTaskImpl tokenRefresherTask;

    @Override
    public Uni<ExecuteTokenRefresherTaskResponse> execute(final ExecuteTokenRefresherTaskRequest request) {
        log.debug("{}", request);

        final var taskArguments = new TokenRefresherTaskArguments();
        return executeTaskOperation.executeFailSafe(tokenRefresherTask, taskArguments)
                .map(ExecuteTokenRefresherTaskResponse::new);
    }
}
