package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRefreshDispatcherTokenTaskMethod {
    Uni<ExecuteRefreshDispatcherTokenTaskResponse> execute(ExecuteRefreshDispatcherTokenTaskRequest request);
}
