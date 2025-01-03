package com.omgservers.dispatcher.service.task;

import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskResponse;
import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TaskService {

    Uni<ExecuteExpiredConnectionsHandlerTaskResponse> execute(
            @Valid ExecuteExpiredConnectionsHandlerTaskRequest request);

    Uni<ExecuteRefreshDispatcherTokenTaskResponse> execute(
            @Valid ExecuteRefreshDispatcherTokenTaskRequest request);
}
