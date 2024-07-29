package com.omgservers.service.module.system.impl.service.taskService.impl.method.executePoolTask;

import com.omgservers.schema.service.system.task.ExecutePoolTaskRequest;
import com.omgservers.schema.service.system.task.ExecutePoolTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecutePoolTaskMethod {
    Uni<ExecutePoolTaskResponse> executePoolTask(ExecutePoolTaskRequest request);
}
