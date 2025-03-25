package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import io.smallrye.mutiny.Uni;

public interface FetchDeploymentOperation {
    Uni<FetchDeploymentResult> execute(Long deploymentId);
}
