package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;

public interface CreateDeploymentLobbyAssignmentOperation {
    DeploymentLobbyAssignmentModel execute(DeploymentLobbyResourceModel lobbyResource,
                                           DeploymentRequestModel deploymentRequest);
}
