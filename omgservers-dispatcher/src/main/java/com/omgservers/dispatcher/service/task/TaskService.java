package com.omgservers.dispatcher.service.task;

import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(@Valid ExecuteIdleConnectionsHandlerTaskRequest request);
}
