package com.omgservers.service.server.service.task.impl.method.executeRelayTask;

import com.omgservers.schema.service.system.task.ExecuteRelayTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRelayTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRelayTaskMethod {
    Uni<ExecuteRelayTaskResponse> executeRelayTask(ExecuteRelayTaskRequest request);
}
