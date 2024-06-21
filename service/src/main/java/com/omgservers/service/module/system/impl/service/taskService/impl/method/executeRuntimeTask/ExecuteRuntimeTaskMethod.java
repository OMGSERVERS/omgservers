package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRuntimeTask;

import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRuntimeTaskMethod {
    Uni<ExecuteRuntimeTaskResponse> executeRuntimeTask(ExecuteRuntimeTaskRequest request);
}
