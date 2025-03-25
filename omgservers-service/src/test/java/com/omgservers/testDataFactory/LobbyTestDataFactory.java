package com.omgservers.testDataFactory;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
import com.omgservers.service.factory.match.MatchModelFactory;
import com.omgservers.service.shard.lobby.service.testInterface.LobbyServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyTestDataFactory {

    final LobbyServiceTestInterface lobbyService;

    final LobbyModelFactory lobbyModelFactory;

    public LobbyModel createLobby(final DeploymentLobbyResourceModel deploymentLobbyResource) {
        final var deploymentId = deploymentLobbyResource.getDeploymentId();
        final var lobbyId = deploymentLobbyResource.getLobbyId();

        final var lobby = lobbyModelFactory.create(lobbyId, deploymentId);
        final var syncLobbyRequest = new SyncLobbyRequest(lobby);
        lobbyService.syncLobby(syncLobbyRequest);
        return lobby;
    }
}
