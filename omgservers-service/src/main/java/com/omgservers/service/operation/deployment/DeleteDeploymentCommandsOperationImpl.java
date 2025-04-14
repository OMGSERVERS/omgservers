package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.shard.deployment.deploymentCommand.DeleteDeploymentCommandRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.DeleteDeploymentCommandResponse;
import com.omgservers.schema.shard.deployment.deploymentCommand.ViewDeploymentCommandsRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.ViewDeploymentCommandsResponse;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteDeploymentCommandsOperationImpl implements DeleteDeploymentCommandsOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final Long deploymentId) {
        return viewDeploymentCommands(deploymentId)
                .flatMap(deploymentCommands -> Multi.createFrom().iterable(deploymentCommands)
                        .onItem().transformToUniAndConcatenate(deploymentCommand -> {
                            final var deploymentCommandId = deploymentCommand.getId();
                            return deleteDeploymentCommands(deploymentId, deploymentCommandId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                deploymentId,
                                                deploymentCommandId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<DeploymentCommandModel>> viewDeploymentCommands(final Long deploymentId) {
        final var request = new ViewDeploymentCommandsRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentCommandsResponse::getDeploymentCommands);
    }

    Uni<Boolean> deleteDeploymentCommands(final Long deploymentId, final Long id) {
        final var request = new DeleteDeploymentCommandRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentCommandResponse::getDeleted);
    }
}
