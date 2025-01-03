package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteExpiredConnectionHandlerTaskMethod {
    Uni<ExecuteExpiredConnectionsHandlerTaskResponse> execute(ExecuteExpiredConnectionsHandlerTaskRequest request);
}
