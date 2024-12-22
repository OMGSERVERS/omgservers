package com.omgservers.service.service.task.impl.method.executeBootstrapTask;

import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteBootstrapTaskMethod {
    Uni<ExecuteBootstrapTaskResponse> execute(ExecuteBootstrapTaskRequest request);
}
