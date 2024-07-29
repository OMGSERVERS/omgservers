package com.omgservers.service.server.service.task.impl.method.executePoolTask;

import com.omgservers.schema.service.system.task.ExecutePoolTaskRequest;
import com.omgservers.schema.service.system.task.ExecutePoolTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecutePoolTaskMethod {
    Uni<ExecutePoolTaskResponse> executePoolTask(ExecutePoolTaskRequest request);
}
