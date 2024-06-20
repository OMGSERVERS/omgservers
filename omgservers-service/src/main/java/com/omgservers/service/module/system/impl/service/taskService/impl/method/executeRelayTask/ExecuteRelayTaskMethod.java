package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRelayTask;

import com.omgservers.model.dto.system.task.ExecuteRelayTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteRelayTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteRelayTaskMethod {
    Uni<ExecuteRelayTaskResponse> executeRelayTask(ExecuteRelayTaskRequest request);
}
