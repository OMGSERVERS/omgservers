package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class EnsureMinMatchmakersOperationImpl implements EnsureMinMatchmakersOperation {

    final CreateMatchmakerResourceOperation createMatchmakerResourceOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var countMatchmakers = fetchDeploymentResult.deploymentState().getDeploymentMatchmakerResources().size();
        final var minMatchmakers = 1;
        if (countMatchmakers < minMatchmakers) {
            log.info("Current matchmaker count \"{}\" is below the minimum \"{}\", create a new one",
                    countMatchmakers, minMatchmakers);

            createMatchmakerResourceOperation.execute(fetchDeploymentResult, handleDeploymentResult);
        }
    }
}
