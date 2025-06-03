package com.omgservers.dispatcher.server.task;

import com.omgservers.dispatcher.server.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.server.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(@Valid ExecuteIdleConnectionsHandlerTaskRequest request);
}
