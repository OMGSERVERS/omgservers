package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateResponse;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class UpdateDeploymentOperationImpl implements UpdateDeploymentOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = handleDeploymentResult.deploymentId();
        final var deploymentChangeOfState = handleDeploymentResult.deploymentChangeOfState();

        return updateDeploymentState(deploymentId, deploymentChangeOfState)
                .replaceWithVoid();
    }

    Uni<Boolean> updateDeploymentState(final Long deploymentId,
                                       final DeploymentChangeOfStateDto deploymentChangeOfState) {
        final var request = new UpdateDeploymentStateRequest(deploymentId, deploymentChangeOfState);
        return deploymentShard.getService().execute(request)
                .map(UpdateDeploymentStateResponse::getUpdated);
    }
}
