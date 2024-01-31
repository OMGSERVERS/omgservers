package com.omgservers.service.module.player.impl;

import com.omgservers.service.module.player.PlayerModule;
import com.omgservers.service.module.player.impl.service.playerService.PlayerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerModuleImpl implements PlayerModule {

    final PlayerService playerService;

    @Override
    public PlayerService getPlayerService() {
        return playerService;
    }
}
