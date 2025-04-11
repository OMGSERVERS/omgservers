package com.omgservers.dispatcher.service.task.impl;

import com.omgservers.dispatcher.service.task.TaskService;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import com.omgservers.dispatcher.service.task.impl.method.ExecuteIdleConnectionHandlerTaskMethod;
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

    @Override
    public Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(
            @Valid final ExecuteIdleConnectionsHandlerTaskRequest request) {
        return executeIdleConnectionHandlerTaskMethod.execute(request);
    }
}
