package com.omgservers.service.server.task.impl.method.executeBootstrapTask;

import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteBootstrapTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteBootstrapTaskMethod {
    Uni<ExecuteBootstrapTaskResponse> execute(ExecuteBootstrapTaskRequest request);
}
