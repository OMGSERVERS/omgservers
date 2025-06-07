package com.omgservers.connector.server.task;

import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteMessageInterchangerTaskResponse;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskRequest;
import com.omgservers.connector.server.task.dto.ExecuteTokenRefresherTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(@Valid ExecuteIdleConnectionsHandlerTaskRequest request);

    Uni<ExecuteMessageInterchangerTaskResponse> execute(@Valid ExecuteMessageInterchangerTaskRequest request);

    Uni<ExecuteTokenRefresherTaskResponse> execute(@Valid ExecuteTokenRefresherTaskRequest request);
}
