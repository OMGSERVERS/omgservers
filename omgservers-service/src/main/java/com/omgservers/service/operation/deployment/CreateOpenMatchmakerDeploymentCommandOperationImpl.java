package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentCommand.body.OpenMatchmakerDeploymentCommandBodyDto;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import com.omgservers.service.factory.deployment.DeploymentCommandModelFactory;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateOpenMatchmakerDeploymentCommandOperationImpl implements CreateOpenMatchmakerDeploymentCommandOperation {

    final DeploymentShard deploymentShard;

    final DeploymentCommandModelFactory deploymentCommandModelFactory;

    @Override
    public Uni<Boolean> execute(final Long deploymentId,
                                final Long matchmakerId) {
        final var commandBody = new OpenMatchmakerDeploymentCommandBodyDto(matchmakerId);

        final var idempotencyKey = commandBody.getQualifier() + "/" + matchmakerId;
        final var deploymentCommand = deploymentCommandModelFactory.create(deploymentId,
                commandBody,
                idempotencyKey);

        final var request = new SyncDeploymentCommandRequest(deploymentCommand);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentCommandResponse::getCreated);
    }

    @Override
    public Uni<Boolean> executeFailSafe(final Long deploymentId,
                                        final Long matchmakerId) {
        return execute(deploymentId, matchmakerId)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, matchmakerId={}, {}:{}",
                            matchmakerId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
