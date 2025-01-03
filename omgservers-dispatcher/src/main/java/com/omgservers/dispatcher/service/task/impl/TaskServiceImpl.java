package com.omgservers.dispatcher.service.task.impl;

import com.omgservers.dispatcher.service.task.TaskService;
import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskResponse;
import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskResponse;
import com.omgservers.dispatcher.service.task.impl.method.ExecuteExpiredConnectionHandlerTaskMethod;
import com.omgservers.dispatcher.service.task.impl.method.ExecuteRefreshDispatcherTokenTaskMethod;
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

    final ExecuteExpiredConnectionHandlerTaskMethod executeExpiredConnectionHandlerTaskMethod;
    final ExecuteRefreshDispatcherTokenTaskMethod executeRefreshDispatcherTokenTaskMethod;

    @Override
    public Uni<ExecuteExpiredConnectionsHandlerTaskResponse> execute(
            @Valid final ExecuteExpiredConnectionsHandlerTaskRequest request) {
        return executeExpiredConnectionHandlerTaskMethod.execute(request);
    }

    @Override
    public Uni<ExecuteRefreshDispatcherTokenTaskResponse> execute(
            @Valid final ExecuteRefreshDispatcherTokenTaskRequest request) {
        return executeRefreshDispatcherTokenTaskMethod.execute(request);
    }
}
