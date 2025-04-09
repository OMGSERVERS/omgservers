package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;

public interface DeploymentCommandHandler {

    DeploymentCommandQualifierEnum getQualifier();

    boolean handle(FetchDeploymentResult fetchDeploymentResult,
                   HandleDeploymentResult handleDeploymentResult,
                   DeploymentCommandModel deploymentCommand);
}
