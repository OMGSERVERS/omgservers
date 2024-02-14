package com.omgservers.service.module.lobby.impl;

import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.shortcutService.ShortcutService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LobbyModuleImpl implements LobbyModule {

    final ShortcutService shortcutService;
    final LobbyService lobbyService;

    public LobbyService getLobbyService() {
        return lobbyService;
    }

    public ShortcutService getShortcutService() {
        return shortcutService;
    }
}
