package com.omgservers.connector.server.task.impl;

import com.omgservers.connector.server.task.TaskService;
import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskResponse;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskResponse;
import com.omgservers.connector.server.task.impl.method.executeIdleConnectionHandlerTask.ExecuteIdleConnectionHandlerTaskMethod;
import com.omgservers.connector.server.task.impl.method.executeMessageInterchangerTask.ExecuteMessageInterchangerTaskMethod;
import com.omgservers.connector.server.task.impl.method.executeTokenRefresherTask.ExecuteTokenRefresherTaskMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TaskServiceImpl implements TaskService {

    final ExecuteIdleConnectionHandlerTaskMethod executeIdleConnectionHandlerTaskMethod;
    final ExecuteMessageInterchangerTaskMethod executeMessageInterchangerTaskMethod;
    final ExecuteTokenRefresherTaskMethod executeTokenRefresherTaskMethod;

    @Override
    public Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(
            @Valid final ExecuteIdleConnectionsHandlerTaskRequest request) {
        return executeIdleConnectionHandlerTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteMessageInterchangerTaskResponse> execute(
            @Valid final ExecuteMessageInterchangerTaskRequest request) {
        return executeMessageInterchangerTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteTokenRefresherTaskResponse> execute(@Valid final ExecuteTokenRefresherTaskRequest request) {
        return executeTokenRefresherTaskMethod.execute(request);
    }
}
