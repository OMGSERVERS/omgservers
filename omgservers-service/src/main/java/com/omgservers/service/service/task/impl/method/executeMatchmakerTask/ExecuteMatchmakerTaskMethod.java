package com.omgservers.service.service.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteMatchmakerTaskMethod {
    Uni<ExecuteMatchmakerTaskResponse> executeMatchmakerTask(ExecuteMatchmakerTaskRequest request);
}
