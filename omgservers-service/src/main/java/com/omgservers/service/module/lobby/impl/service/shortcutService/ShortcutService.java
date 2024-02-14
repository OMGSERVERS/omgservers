package com.omgservers.service.module.lobby.impl.service.shortcutService;

import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import io.smallrye.mutiny.Uni;

public interface ShortcutService {

    Uni<LobbyModel> getLobby(Long lobbyId);

    Uni<Boolean> deleteLobby(Long lobbyId);

    Uni<LobbyRuntimeModel> getLobbyRuntime(Long lobbyId, Long id);

    Uni<LobbyRuntimeModel> findLobbyRuntime(Long lobbyId, Long runtimeId);

    Uni<Boolean> syncLobbyRuntime(LobbyRuntimeModel lobbyRuntime);

    Uni<Boolean> deleteLobbyRuntime(Long lobbyId, Long id);

    Uni<Boolean> findAndDeleteLobbyRuntime(Long lobbyId, Long runtimeId);
}
