package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.factory.deployment.DeploymentLobbyResourceModelFactory;
import com.omgservers.service.factory.deployment.DeploymentMatchmakerResourceModelFactory;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleDeploymentRequestsOperationImpl implements HandleDeploymentRequestsOperation {

    final ExecuteDeploymentAssignersOperation executeDeploymentAssignersOperation;
    final CreateMatchmakerAssignerOperation createMatchmakerAssignerOperation;
    final CreateLobbyAssignerOperation createLobbyAssignerOperation;

    final DeploymentMatchmakerResourceModelFactory deploymentMatchmakerResourceModelFactory;
    final DeploymentLobbyResourceModelFactory deploymentLobbyResourceModelFactory;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentRequests = fetchDeploymentResult.deploymentState().getDeploymentRequests();
        if (deploymentRequests.isEmpty()) {
            log.trace("Deployment \"{}\" has no requests to process", deploymentId);
        } else {
            final var lobbyAssigner = createLobbyAssignerOperation.execute(fetchDeploymentResult,
                    handleDeploymentResult);
            final var matchmakerAssigner = createMatchmakerAssignerOperation.execute(fetchDeploymentResult,
                    handleDeploymentResult);

            final var executeDeploymentAssignersResult = executeDeploymentAssignersOperation.execute(deploymentRequests,
                    lobbyAssigner,
                    matchmakerAssigner,
                    handleDeploymentResult);

            // TODO: Introduce and check limits

            if (executeDeploymentAssignersResult.lobbyNotAssigned()) {
                final var noPendingLobbies = fetchDeploymentResult.deploymentState()
                        .getDeploymentLobbyResources().stream()
                        .map(DeploymentLobbyResourceModel::getStatus)
                        .noneMatch(DeploymentLobbyResourceStatusEnum.PENDING::equals);
                if (noPendingLobbies) {
                    final var deploymentLobbyResource = deploymentLobbyResourceModelFactory.create(deploymentId);
                    handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToSync()
                            .add(deploymentLobbyResource);
                }
            }

            if (executeDeploymentAssignersResult.matchmakerNotAssigned()) {
                final var noPendingMatchmakers = fetchDeploymentResult.deploymentState()
                        .getDeploymentMatchmakerResources().stream()
                        .map(DeploymentMatchmakerResourceModel::getStatus)
                        .noneMatch(DeploymentMatchmakerResourceStatusEnum.PENDING::equals);

                if (noPendingMatchmakers) {
                    final var deploymentMatchmakerResource = deploymentMatchmakerResourceModelFactory.create(deploymentId);
                    handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToSync()
                            .add(deploymentMatchmakerResource);
                }
            }
        }
    }
}
