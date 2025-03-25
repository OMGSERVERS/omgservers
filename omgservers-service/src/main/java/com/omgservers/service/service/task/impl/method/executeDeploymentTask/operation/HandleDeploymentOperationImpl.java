package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.HandleDeploymentCommandsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleDeploymentOperationImpl implements HandleDeploymentOperation {

    final HandleDeploymentRequestsOperation handleDeploymentRequestsOperation;
    final HandleDeploymentCommandsOperation handleDeploymentCommandsOperation;
    final CloseDanglingMatchmakersOperation closeDanglingMatchmakersOperation;
    final DeleteClosedMatchmakersOperation deleteClosedMatchmakersOperation;
    final CloseDanglingLobbiesOperation closeDanglingLobbiesOperation;
    final DeleteClosedLobbiesOperation deleteClosedLobbiesOperation;

    @Override
    public HandleDeploymentResult execute(final FetchDeploymentResult fetchDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();
        final var handleDeploymentResult = new HandleDeploymentResult(deploymentId,
                new DeploymentChangeOfStateDto());

        deleteClosedMatchmakersOperation.execute(fetchDeploymentResult, handleDeploymentResult);
        deleteClosedLobbiesOperation.execute(fetchDeploymentResult, handleDeploymentResult);

        closeDanglingMatchmakersOperation.execute(fetchDeploymentResult, handleDeploymentResult);
        closeDanglingLobbiesOperation.execute(fetchDeploymentResult, handleDeploymentResult);

        handleDeploymentCommandsOperation.execute(fetchDeploymentResult, handleDeploymentResult);
        handleDeploymentRequestsOperation.execute(fetchDeploymentResult, handleDeploymentResult);

        return handleDeploymentResult;
    }
}
