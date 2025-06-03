package com.omgservers.connector.server.task.impl.method.executeMessageInterchangerTask;

import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteMessageInterchangerTaskMethod {
    Uni<ExecuteMessageInterchangerTaskResponse> execute(ExecuteMessageInterchangerTaskRequest request);
}
