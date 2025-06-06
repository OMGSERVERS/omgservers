package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;

public interface CloseDeploymentResourcesOperation {
    void execute(FetchDeploymentResult fetchDeploymentResult,
                 HandleDeploymentResult handleDeploymentResult);
}
