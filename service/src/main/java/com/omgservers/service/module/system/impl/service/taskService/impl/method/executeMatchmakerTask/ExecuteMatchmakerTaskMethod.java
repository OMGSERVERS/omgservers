package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask;

import com.omgservers.schema.service.system.task.ExecuteMatchmakerTaskRequest;
import com.omgservers.schema.service.system.task.ExecuteMatchmakerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteMatchmakerTaskMethod {
    Uni<ExecuteMatchmakerTaskResponse> executeMatchmakerTask(ExecuteMatchmakerTaskRequest request);
}
