package com.omgservers.service.module.lobby;

import com.omgservers.service.module.lobby.impl.service.matchmakerService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.shortcutService.ShortcutService;

public interface LobbyModule {

    LobbyService getLobbyService();

    ShortcutService getShortcutService();
}
