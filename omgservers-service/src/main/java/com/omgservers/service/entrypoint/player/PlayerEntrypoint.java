package com.omgservers.service.entrypoint.player;

import com.omgservers.service.entrypoint.player.impl.service.playerService.PlayerService;

public interface PlayerEntrypoint {
    PlayerService getPlayerService();
}
