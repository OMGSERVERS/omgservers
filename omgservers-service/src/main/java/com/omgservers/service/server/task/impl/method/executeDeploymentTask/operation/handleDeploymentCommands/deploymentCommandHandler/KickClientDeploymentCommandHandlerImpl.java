package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.schema.model.deploymentCommand.body.KickClientDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.DeploymentCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class KickClientDeploymentCommandHandlerImpl implements DeploymentCommandHandler {

    @Override
    public DeploymentCommandQualifierEnum getQualifier() {
        return DeploymentCommandQualifierEnum.KICK_CLIENT;
    }

    @Override
    public boolean handle(final FetchDeploymentResult fetchDeploymentResult,
                          final HandleDeploymentResult handleDeploymentResult,
                          final DeploymentCommandModel deploymentCommand) {
        log.trace("Handle command, {}", deploymentCommand);

        final var body = (KickClientDeploymentCommandBodyDto) deploymentCommand.getBody();
        final var clientId = body.getClientId();

        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentLobbyAssignmentsToDelete = fetchDeploymentResult
                .deploymentState().getDeploymentLobbyAssignments().stream()
                .filter(deploymentLobbyAssignment -> deploymentLobbyAssignment.getClientId().equals(clientId))
                .map(DeploymentLobbyAssignmentModel::getId)
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyAssignmentToDelete()
                .addAll(deploymentLobbyAssignmentsToDelete);

        log.info("Client \"{}\" must be kicked from deployment \"{}\", assignments queued for deletion",
                clientId,
                deploymentId);

        return true;
    }
}
