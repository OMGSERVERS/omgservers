package com.omgservers.service.service.task.impl.method.executeRuntimeTask;

import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRuntimeTaskMethod {
    Uni<ExecuteRuntimeTaskResponse> execute(ExecuteRuntimeTaskRequest request);
}
