package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceConfigDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.service.factory.deployment.DeploymentLobbyResourceModelFactory;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateLobbyResourceOperationImpl implements CreateLobbyResourceOperation {

    final DeploymentLobbyResourceModelFactory deploymentLobbyResourceModelFactory;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentConfig = fetchDeploymentResult.deploymentState().getDeployment().getConfig();

        if (handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToSync().isEmpty()) {

            final var currentLobbies = fetchDeploymentResult.deploymentState().getDeploymentLobbyResources().size();
            final var maxLobbies = deploymentConfig.getMaxLobbies();
            if (currentLobbies >= maxLobbies) {
                log.info("Reached maximum number of lobbies \"{}\", skip operation", maxLobbies);
                return;
            }

            final var deploymentId = fetchDeploymentResult.deploymentId();

            final var noPendingLobbies = fetchDeploymentResult.deploymentState()
                    .getDeploymentLobbyResources().stream()
                    .map(DeploymentLobbyResourceModel::getStatus)
                    .noneMatch(DeploymentLobbyResourceStatusEnum.PENDING::equals);

            if (noPendingLobbies) {
                final var deploymentLobbyResourceConfig = new DeploymentLobbyResourceConfigDto();
                final var deploymentLobbyResource = deploymentLobbyResourceModelFactory.create(deploymentId,
                        deploymentLobbyResourceConfig);
                handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToSync()
                        .add(deploymentLobbyResource);
            }
        }
    }
}
