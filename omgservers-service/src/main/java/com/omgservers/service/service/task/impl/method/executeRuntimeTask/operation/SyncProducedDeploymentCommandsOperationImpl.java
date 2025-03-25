package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandResponse;
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
class SyncProducedDeploymentCommandsOperationImpl implements SyncProducedDeploymentCommandsOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final List<DeploymentCommandModel> producedDeploymentCommands) {
        return Multi.createFrom().iterable(producedDeploymentCommands)
                .onItem().transformToUniAndConcatenate(producedDeploymentCommand -> {
                    final var deploymentId = producedDeploymentCommand.getDeploymentId();
                    return syncDeploymentCommand(producedDeploymentCommand)
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.error("Failed to sync, deploymentId={}, {}:{}",
                                        deploymentId,
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return Boolean.FALSE;
                            });
                })
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> syncDeploymentCommand(final DeploymentCommandModel deploymentCommand) {
        final var request = new SyncDeploymentCommandRequest(deploymentCommand);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentCommandResponse::getCreated);
    }
}
