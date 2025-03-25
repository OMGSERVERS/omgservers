package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;

public interface CreateDeploymentMatchmakerAssignmentOperation {
    DeploymentMatchmakerAssignmentModel execute(DeploymentMatchmakerResourceModel matchmakerResource,
                                                DeploymentRequestModel deploymentRequest);
}
