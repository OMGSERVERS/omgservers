package com.omgservers.dispatcher.service.task.impl;

import com.omgservers.dispatcher.service.task.TaskService;
import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskResponse;
import com.omgservers.dispatcher.service.task.impl.method.ExecuteDispatcherTaskMethod;
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

    final ExecuteDispatcherTaskMethod executeDispatcherTaskMethod;

    @Override
    public Uni<ExecuteDispatcherTaskResponse> execute(@Valid final ExecuteDispatcherTaskRequest request) {
        return executeDispatcherTaskMethod.execute(request);
    }
}
