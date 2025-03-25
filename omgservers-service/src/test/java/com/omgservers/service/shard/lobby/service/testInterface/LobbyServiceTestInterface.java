package com.omgservers.service.shard.lobby.service.testInterface;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
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
        return lobbyService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncLobbyResponse syncLobby(final SyncLobbyRequest request) {
        return lobbyService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncLobbyResponse syncLobbyWithIdempotency(final SyncLobbyRequest request) {
        return lobbyService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteLobbyResponse deleteLobby(final DeleteLobbyRequest request) {
        return lobbyService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
