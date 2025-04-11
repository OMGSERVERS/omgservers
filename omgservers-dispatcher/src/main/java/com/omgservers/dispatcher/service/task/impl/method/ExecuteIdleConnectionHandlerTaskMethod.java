package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteIdleConnectionHandlerTaskMethod {
    Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(ExecuteIdleConnectionsHandlerTaskRequest request);
}
