package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteDispatcherTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteDispatcherTaskMethod {
    Uni<ExecuteDispatcherTaskResponse> execute(ExecuteDispatcherTaskRequest request);
}
