package com.omgservers.service.service.task.impl.method.executeDeploymentTask.component;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;

public class LobbyAssigner extends DeploymentAssigner<DeploymentLobbyResourceModel,
        DeploymentLobbyAssignmentModel,
        DeploymentRequestModel> {

    public LobbyAssigner(int maxAssignments) {
        super(maxAssignments);
    }
}
