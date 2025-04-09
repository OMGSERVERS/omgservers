package com.omgservers.service.server.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteMatchmakerTaskMethod {
    Uni<ExecuteMatchmakerTaskResponse> execute(ExecuteMatchmakerTaskRequest request);
}
