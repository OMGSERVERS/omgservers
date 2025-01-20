package com.omgservers.service.shard.lobby.impl.service.lobbyService.testInterface;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.LobbyService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final LobbyService lobbyService;

    public GetLobbyResponse getLobby(final GetLobbyRequest request) {
        return lobbyService.getLobby(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncLobbyResponse syncLobby(final SyncLobbyRequest request) {
        return lobbyService.syncLobby(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteLobbyResponse deleteLobby(final DeleteLobbyRequest request) {
        return lobbyService.deleteLobby(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetLobbyRuntimeRefResponse getLobbyRuntimeRef(final GetLobbyRuntimeRefRequest request) {
        return lobbyService.getLobbyRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindLobbyRuntimeRefResponse findLobbyRuntimeRef(final FindLobbyRuntimeRefRequest request) {
        return lobbyService.findLobbyRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncLobbyRuntimeRefResponse syncLobbyRuntimeRef(final SyncLobbyRuntimeRefRequest request) {
        return lobbyService.syncLobbyRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteLobbyRuntimeRefResponse deleteLobbyRuntimeRef(final DeleteLobbyRuntimeRefRequest request) {
        return lobbyService.deleteLobbyRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
