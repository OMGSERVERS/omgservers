package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentCommand.body.OpenLobbyDeploymentCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.OpenMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.service.factory.deployment.DeploymentCommandModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateOpenLobbyDeploymentCommandOperationImpl implements CreateOpenLobbyDeploymentCommandOperation {

    final DeploymentShard deploymentShard;

    final DeploymentCommandModelFactory deploymentCommandModelFactory;

    @Override
    public Uni<Boolean> execute(final Long deploymentId,
                                final Long lobbyId,
                                final String idempotencyKey) {
        final var commandBody = new OpenLobbyDeploymentCommandBodyDto(lobbyId);
        final var deploymentCommand = deploymentCommandModelFactory.create(deploymentId,
                commandBody,
                idempotencyKey);

        final var request = new SyncDeploymentCommandRequest(deploymentCommand);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentCommandResponse::getCreated);
    }

    @Override
    public Uni<Boolean> executeFailSafe(final Long deploymentId,
                                        final Long lobbyId,
                                        final String idempotencyKey) {
        return execute(deploymentId, lobbyId, idempotencyKey)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, matchId={}, {}:{}",
                            lobbyId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
