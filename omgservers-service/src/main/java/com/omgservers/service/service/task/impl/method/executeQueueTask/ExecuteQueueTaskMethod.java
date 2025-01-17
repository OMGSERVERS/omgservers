package com.omgservers.service.service.task.impl.method.executeQueueTask;

import com.omgservers.service.service.task.dto.ExecuteQueueTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteQueueTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteQueueTaskMethod {
    Uni<ExecuteQueueTaskResponse> execute(ExecuteQueueTaskRequest request);
}
