package com.omgservers.service.server.service.task.impl.method.executeRuntimeTask;

import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRuntimeTaskMethod {
    Uni<ExecuteRuntimeTaskResponse> executeRuntimeTask(ExecuteRuntimeTaskRequest request);
}
