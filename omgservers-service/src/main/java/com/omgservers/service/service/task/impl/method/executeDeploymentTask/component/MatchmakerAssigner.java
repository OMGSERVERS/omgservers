package com.omgservers.service.service.task.impl.method.executeDeploymentTask.component;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;

public class MatchmakerAssigner extends DeploymentAssigner<DeploymentMatchmakerResourceModel,
        DeploymentMatchmakerAssignmentModel,
        DeploymentRequestModel> {

    public MatchmakerAssigner(final int maxAssignments) {
        super(maxAssignments);
    }
}
