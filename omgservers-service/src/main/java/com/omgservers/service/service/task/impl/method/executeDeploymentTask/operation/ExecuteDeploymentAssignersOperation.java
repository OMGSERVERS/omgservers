package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.LobbyAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.MatchmakerAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.ExecuteDeploymentAssignersResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;

import java.util.List;

public interface ExecuteDeploymentAssignersOperation {
    ExecuteDeploymentAssignersResult execute(List<DeploymentRequestModel> deploymentRequests,
                                             LobbyAssigner lobbyAssigner,
                                             MatchmakerAssigner matchmakerAssigner,
                                             HandleDeploymentResult handleDeploymentResult);
}
