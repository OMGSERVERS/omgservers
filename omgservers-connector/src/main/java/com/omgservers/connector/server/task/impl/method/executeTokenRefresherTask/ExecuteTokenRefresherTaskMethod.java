package com.omgservers.connector.server.task.impl.method.executeTokenRefresherTask;

import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteTokenRefresherTaskMethod {
    Uni<ExecuteTokenRefresherTaskResponse> execute(ExecuteTokenRefresherTaskRequest request);
}
