package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class EnsureMinLobbiesOperationImpl implements EnsureMinLobbiesOperation {

    final CreateLobbyResourceOperation createLobbyResourceOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentConfig = fetchDeploymentResult.deploymentState().getDeployment().getConfig();

        final var countLobbies = fetchDeploymentResult.deploymentState().getDeploymentLobbyResources().size();
        final var minLobbies = deploymentConfig.getMinLobbies();
        if (countLobbies < minLobbies) {
            log.info("Current lobby count \"{}\" is below the minimum \"{}\", create a new one",
                    countLobbies, minLobbies);

            createLobbyResourceOperation.execute(fetchDeploymentResult, handleDeploymentResult);
        }
    }
}
