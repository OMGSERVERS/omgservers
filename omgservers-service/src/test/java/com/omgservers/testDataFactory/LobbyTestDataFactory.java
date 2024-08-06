package com.omgservers.testDataFactory;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
import com.omgservers.service.factory.lobby.LobbyRuntimeRefModelFactory;
import com.omgservers.service.module.lobby.impl.service.lobbyService.testInterface.LobbyServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyTestDataFactory {

    final LobbyServiceTestInterface lobbyService;

    final LobbyRuntimeRefModelFactory lobbyRuntimeRefModelFactory;
    final LobbyModelFactory lobbyModelFactory;

    public LobbyModel createLobby(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();

        final var lobby = lobbyModelFactory.create(tenantId, versionId);
        final var syncLobbyRequest = new SyncLobbyRequest(lobby);
        lobbyService.syncLobby(syncLobbyRequest);
        return lobby;
    }

    public LobbyModel createLobby(final VersionLobbyRequestModel versionLobbyRequest) {
        final var tenantId = versionLobbyRequest.getTenantId();
        final var versionId = versionLobbyRequest.getVersionId();
        final var lobbyId = versionLobbyRequest.getLobbyId();

        final var lobby = lobbyModelFactory.create(lobbyId, tenantId, versionId);
        final var syncLobbyRequest = new SyncLobbyRequest(lobby);
        lobbyService.syncLobby(syncLobbyRequest);
        return lobby;
    }

    public LobbyRuntimeRefModel createLobbyRuntimeRef(final LobbyModel lobby,
                                                      final RuntimeModel runtime) {
        final var lobbyId = lobby.getId();
        final var runtimeId = runtime.getId();

        final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobbyId, runtimeId);
        final var syncLobbyRuntimeRefRequest = new SyncLobbyRuntimeRefRequest(lobbyRuntimeRef);
        lobbyService.syncLobbyRuntimeRef(syncLobbyRuntimeRefRequest);
        return lobbyRuntimeRef;
    }
}
