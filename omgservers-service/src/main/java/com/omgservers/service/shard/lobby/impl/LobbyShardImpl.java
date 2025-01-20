package com.omgservers.service.shard.lobby.impl;

import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.LobbyService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LobbyShardImpl implements LobbyShard {

    final LobbyService lobbyService;

    public LobbyService getService() {
        return lobbyService;
    }
}
