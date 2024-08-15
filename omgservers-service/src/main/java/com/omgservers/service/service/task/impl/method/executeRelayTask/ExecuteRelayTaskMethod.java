package com.omgservers.service.service.task.impl.method.executeRelayTask;

import com.omgservers.service.service.task.dto.ExecuteRelayTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRelayTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRelayTaskMethod {
    Uni<ExecuteRelayTaskResponse> executeRelayTask(ExecuteRelayTaskRequest request);
}
