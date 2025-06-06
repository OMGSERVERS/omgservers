package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentChangeOfState.DeploymentMatchmakerResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CloseDeploymentResourcesOperationImpl implements CloseDeploymentResourcesOperation {

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        closeMatchmakerResources(fetchDeploymentResult, handleDeploymentResult);
        closeLobbyResources(fetchDeploymentResult, handleDeploymentResult);
    }

    void closeMatchmakerResources(final FetchDeploymentResult fetchDeploymentResult,
                                  final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        final var deploymentMatchmakerResourcesToUpdateStatus = deploymentState
                .getDeploymentMatchmakerResources().stream()
                .filter(deploymentMatchmakerResource ->
                        deploymentMatchmakerResource.getStatus().equals(DeploymentMatchmakerResourceStatusEnum.CREATED))
                .map(DeploymentMatchmakerResourceModel::getId)
                .toList();

        if (!deploymentMatchmakerResourcesToUpdateStatus.isEmpty()) {
            deploymentMatchmakerResourcesToUpdateStatus.forEach(deploymentMatchmakerResourceId -> {
                final var dtoToUpdateStatus = new DeploymentMatchmakerResourceToUpdateStatusDto(
                        deploymentMatchmakerResourceId,
                        DeploymentMatchmakerResourceStatusEnum.CLOSED);

                handleDeploymentResult.deploymentChangeOfState()
                        .getDeploymentMatchmakerResourcesToUpdateStatus()
                        .add(dtoToUpdateStatus);
            });

            log.info("\"{}\" matchmaker resources from deployment \"{}\" " +
                            "marked as closed",
                    deploymentMatchmakerResourcesToUpdateStatus.size(), deploymentId);
        }
    }

    void closeLobbyResources(final FetchDeploymentResult fetchDeploymentResult,
                             final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        final var deploymentLobbyResourcesToUpdateStatus = deploymentState
                .getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource ->
                        deploymentLobbyResource.getStatus().equals(DeploymentLobbyResourceStatusEnum.CREATED))
                .map(DeploymentLobbyResourceModel::getId)
                .toList();

        if (!deploymentLobbyResourcesToUpdateStatus.isEmpty()) {
            deploymentLobbyResourcesToUpdateStatus.forEach(deploymentLobbyResourceId -> {
                final var dtoToUpdateStatus = new DeploymentLobbyResourceToUpdateStatusDto(
                        deploymentLobbyResourceId,
                        DeploymentLobbyResourceStatusEnum.CLOSED);

                handleDeploymentResult.deploymentChangeOfState()
                        .getDeploymentLobbyResourcesToUpdateStatus()
                        .add(dtoToUpdateStatus);
            });

            log.info("\"{}\" lobby resources from deployment \"{}\" " +
                            "marked as closed",
                    deploymentLobbyResourcesToUpdateStatus.size(), deploymentId);
        }
    }
}
