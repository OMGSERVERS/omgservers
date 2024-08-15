package com.omgservers.service.service.task.impl.method.executePoolTask;

import com.omgservers.service.service.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.service.task.dto.ExecutePoolTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecutePoolTaskMethod {
    Uni<ExecutePoolTaskResponse> executePoolTask(ExecutePoolTaskRequest request);
}
