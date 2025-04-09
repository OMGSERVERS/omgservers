package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.factory.deployment.DeploymentMatchmakerAssignmentModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateDeploymentMatchmakerAssignmentOperationImpl implements CreateDeploymentMatchmakerAssignmentOperation {

    final DeploymentMatchmakerAssignmentModelFactory deploymentMatchmakerAssignmentModelFactory;

    @Override
    public DeploymentMatchmakerAssignmentModel execute(final DeploymentMatchmakerResourceModel matchmakerResource,
                                                       final DeploymentRequestModel deploymentRequest) {
        final var deploymentId = matchmakerResource.getDeploymentId();
        final var matchmakerId = matchmakerResource.getMatchmakerId();
        final var clientId = deploymentRequest.getClientId();
        final var idempotencyKey = deploymentRequest.getId().toString();

        final var deploymentMatchmakerAssignment = deploymentMatchmakerAssignmentModelFactory.create(deploymentId,
                        clientId,
                        matchmakerId,
                        idempotencyKey);

        return deploymentMatchmakerAssignment;
    }
}
