package com.omgservers.service.entrypoint.player.impl;

import com.omgservers.service.entrypoint.player.impl.service.playerService.PlayerService;
import com.omgservers.service.entrypoint.player.PlayerEntrypoint;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerEntrypointImpl implements PlayerEntrypoint {

    final PlayerService playerService;

    @Override
    public PlayerService getService() {
        return playerService;
    }
}
