package com.omgservers.service.service.task.impl.method.executeEventHandlerTask;

import com.omgservers.service.service.task.dto.ExecuteEventHandlerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteEventHandlerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteEventHandlerTaskMethod {
    Uni<ExecuteEventHandlerTaskResponse> execute(ExecuteEventHandlerTaskRequest request);
}
