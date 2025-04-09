package com.omgservers.service.server.task.impl.method.executeDeploymentTask;

import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteDeploymentTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteDeploymentTaskMethod {
    Uni<ExecuteDeploymentTaskResponse> execute(ExecuteDeploymentTaskRequest request);
}
