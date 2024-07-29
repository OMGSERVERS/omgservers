package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRelayTask;

import com.omgservers.schema.service.system.task.ExecuteRelayTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteRelayTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRelayTaskMethod {
    Uni<ExecuteRelayTaskResponse> executeRelayTask(ExecuteRelayTaskRequest request);
}
