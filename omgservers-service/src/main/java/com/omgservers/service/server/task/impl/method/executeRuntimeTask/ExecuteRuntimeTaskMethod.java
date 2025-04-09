package com.omgservers.service.server.task.impl.method.executeRuntimeTask;

import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRuntimeTaskMethod {
    Uni<ExecuteRuntimeTaskResponse> execute(ExecuteRuntimeTaskRequest request);
}
