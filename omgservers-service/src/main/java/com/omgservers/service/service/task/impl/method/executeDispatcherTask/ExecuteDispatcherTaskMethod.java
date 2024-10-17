package com.omgservers.service.service.task.impl.method.executeDispatcherTask;

import com.omgservers.service.service.task.dto.ExecuteDispatcherTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteDispatcherTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteDispatcherTaskMethod {
    Uni<ExecuteDispatcherTaskResponse> execute(ExecuteDispatcherTaskRequest request);
}
