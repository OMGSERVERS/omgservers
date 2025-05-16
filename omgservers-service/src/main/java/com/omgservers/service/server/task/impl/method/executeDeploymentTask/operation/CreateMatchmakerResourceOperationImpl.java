package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.factory.deployment.DeploymentMatchmakerResourceModelFactory;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateMatchmakerResourceOperationImpl implements CreateMatchmakerResourceOperation {

    final DeploymentMatchmakerResourceModelFactory deploymentMatchmakerResourceModelFactory;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentConfig = fetchDeploymentResult.deploymentState().getDeployment().getConfig();
        if (handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToSync().isEmpty()) {

            final var currentMatchmakers = fetchDeploymentResult.deploymentState()
                    .getDeploymentMatchmakerResources().size();
            final var maxMatchmakers = deploymentConfig.getMaxMatchmakers();
            if (currentMatchmakers >= maxMatchmakers) {
                log.info("Reached maximum number of matchmakers \"{}\", skip operation", maxMatchmakers);
                return;
            }

            final var deploymentId = fetchDeploymentResult.deploymentId();

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
