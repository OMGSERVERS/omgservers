package com.omgservers.service.server.task.impl.method.executeEventHandlerTask;

import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteEventHandlerTaskMethod {
    Uni<ExecuteEventHandlerTaskResponse> execute(ExecuteEventHandlerTaskRequest request);
}
