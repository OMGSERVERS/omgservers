package com.omgservers.connector.server.task.impl.method.executeIdleConnectionHandlerTask;

import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteIdleConnectionHandlerTaskMethod {
    Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(ExecuteIdleConnectionsHandlerTaskRequest request);
}
