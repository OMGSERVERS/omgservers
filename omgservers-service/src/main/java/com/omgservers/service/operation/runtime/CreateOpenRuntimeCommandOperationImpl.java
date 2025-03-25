package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.operation.deployment.CreateOpenLobbyDeploymentCommandOperation;
import com.omgservers.service.operation.matchmaker.CreateOpenMatchMatchmakerCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateOpenRuntimeCommandOperationImpl implements CreateOpenRuntimeCommandOperation {

    final CreateOpenLobbyDeploymentCommandOperation createOpenLobbyDeploymentCommandOperation;
    final CreateOpenMatchMatchmakerCommandOperation createOpenMatchMatchmakerCommandOperation;

    @Override
    public Uni<Boolean> execute(final RuntimeModel runtime,
                                final String idempotencyKey) {
        return switch (runtime.getQualifier()) {
            case LOBBY -> {
                final var deploymentId = runtime.getDeploymentId();
                final var lobbyId = runtime.getConfig().getLobby().getLobbyId();
                yield createOpenLobbyDeploymentCommandOperation.execute(deploymentId, lobbyId, idempotencyKey);
            }
            case MATCH -> {
                final var matchmakerId = runtime.getConfig().getMatch().getMatchmakerId();
                final var matchId = runtime.getConfig().getMatch().getMatchId();
                yield createOpenMatchMatchmakerCommandOperation.execute(matchmakerId, matchId, idempotencyKey);
            }
        };
    }
}
