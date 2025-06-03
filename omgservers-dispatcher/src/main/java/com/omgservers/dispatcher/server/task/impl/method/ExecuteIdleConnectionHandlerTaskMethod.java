package com.omgservers.dispatcher.server.task.impl.method;

import com.omgservers.dispatcher.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteIdleConnectionHandlerTaskMethod {
    Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(ExecuteIdleConnectionsHandlerTaskRequest request);
}
