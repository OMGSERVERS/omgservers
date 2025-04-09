package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.body.DeleteLobbyDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentCommand.body.LobbyDeletionReasonEnum;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.MatchDeletionReasonEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.factory.deployment.DeploymentCommandModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleRuntimeLastActivityOperationImpl implements HandleRuntimeLastActivityOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    final DeploymentCommandModelFactory deploymentCommandModelFactory;
    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public void execute(final FetchRuntimeResult fetchRuntimeResult,
                        final HandleRuntimeResult handleRuntimeResult) {

        final var runtimeLastActivity = fetchRuntimeResult.lastActivity();
        final var maxInactiveInterval = getServiceConfigOperation.getServiceConfig().runtimes()
                .inactiveInterval();
        final var currentInactiveInterval = Duration.between(runtimeLastActivity, Instant.now());

        if (currentInactiveInterval.toSeconds() >= maxInactiveInterval) {
            final var runtime = fetchRuntimeResult.runtimeState().getRuntime();
            final var runtimeQualifier = runtime.getQualifier();

            log.info("Runtime \"{}\" detected as inactive, qualifier=\"{}\"",
                    runtime.getId(), runtimeQualifier);

            switch (runtime.getQualifier()) {
                case LOBBY -> {
                    final var deploymentCommand = createDeleteLobbyDeploymentCommand(runtime);
                    handleRuntimeResult.deploymentCommandsToSync().add(deploymentCommand);
                }
                case MATCH -> {
                    final var matchmakerCommand = createDeleteMatchMatchmakerCommand(runtime);
                    handleRuntimeResult.matchmakerCommandsToSync().add(matchmakerCommand);
                }
            }
        }
    }

    DeploymentCommandModel createDeleteLobbyDeploymentCommand(final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        final var deploymentId = runtime.getDeploymentId();
        final var lobbyId = runtime.getConfig().getLobby().getLobbyId();
        final var reason = LobbyDeletionReasonEnum.INACTIVITY;
        final var commandBody = new DeleteLobbyDeploymentCommandBodyDto(lobbyId, reason);
        final var idempotencyKey = commandBody.getQualifier() + "/" + runtimeId.toString();
        final var deploymentCommand = deploymentCommandModelFactory.create(deploymentId,
                commandBody,
                idempotencyKey);
        return deploymentCommand;
    }

    MatchmakerCommandModel createDeleteMatchMatchmakerCommand(final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        final var matchmakerId = runtime.getConfig().getMatch().getMatchmakerId();
        final var matchId = runtime.getConfig().getMatch().getMatchId();
        final var reason = MatchDeletionReasonEnum.INACTIVITY;
        final var commandBody = new DeleteMatchMatchmakerCommandBodyDto(matchId, reason);
        final var idempotencyKey = commandBody.getQualifier() + "/" + runtimeId.toString();
        final var matchmakerCommand = matchmakerCommandModelFactory.create(matchmakerId,
                commandBody,
                idempotencyKey);
        return matchmakerCommand;
    }
}
