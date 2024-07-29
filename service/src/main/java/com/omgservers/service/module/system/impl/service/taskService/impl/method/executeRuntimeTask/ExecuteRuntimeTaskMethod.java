package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRuntimeTask;

import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRuntimeTaskMethod {
    Uni<ExecuteRuntimeTaskResponse> executeRuntimeTask(ExecuteRuntimeTaskRequest request);
}
