package com.omgservers.service.server.task.impl.method.executePoolTask;

import com.omgservers.service.server.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.server.task.dto.ExecutePoolTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecutePoolTaskMethod {
    Uni<ExecutePoolTaskResponse> execute(ExecutePoolTaskRequest request);
}
