package com.omgservers.service.service.task.impl.method.executeDeploymentTask;

import com.omgservers.service.service.task.dto.ExecuteDeploymentTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteDeploymentTaskResponse;
import io.smallrye.mutiny.Uni;

public interface ExecuteDeploymentTaskMethod {
    Uni<ExecuteDeploymentTaskResponse> execute(ExecuteDeploymentTaskRequest request);
}
