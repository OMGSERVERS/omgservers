package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;

public interface HandleDeploymentCommandsOperation {
    void execute(FetchDeploymentResult fetchDeploymentResult,
                 HandleDeploymentResult handleDeploymentResult);
}
