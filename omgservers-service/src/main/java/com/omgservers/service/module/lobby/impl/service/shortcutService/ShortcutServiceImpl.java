package com.omgservers.service.module.lobby.impl.service.shortcutService;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import com.omgservers.service.module.lobby.LobbyModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ShortcutServiceImpl implements ShortcutService {

    final LobbyModule lobbyModule;

    @Override
    public Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    @Override
    public Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().deleteLobby(request)
                .map(DeleteLobbyResponse::getDeleted);
    }

    @Override
    public Uni<LobbyRuntimeModel> getLobbyRuntime(final Long lobbyId, final Long id) {
        final var request = new GetLobbyRuntimeRequest(lobbyId, id);
        return lobbyModule.getLobbyService().getLobbyRuntime(request)
                .map(GetLobbyRuntimeResponse::getLobbyRuntime);
    }

    @Override
    public Uni<LobbyRuntimeModel> findLobbyRuntime(final Long lobbyId, final Long runtimeId) {
        final var request = new FindLobbyRuntimeRequest(lobbyId, runtimeId);
        return lobbyModule.getLobbyService().findLobbyRuntime(request)
                .map(FindLobbyRuntimeResponse::getLobbyRuntime);
    }

    @Override
    public Uni<Boolean> syncLobbyRuntime(final LobbyRuntimeModel lobbyRuntime) {
        final var request = new SyncLobbyRuntimeRequest(lobbyRuntime);
        return lobbyModule.getLobbyService().syncLobbyRuntime(request)
                .map(SyncLobbyRuntimeResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteLobbyRuntime(final Long lobbyId, final Long id) {
        final var request = new DeleteLobbyRuntimeRequest(lobbyId, id);
        return lobbyModule.getLobbyService().deleteLobbyRuntime(request)
                .map(DeleteLobbyRuntimeResponse::getDeleted);
    }

    @Override
    public Uni<Boolean> findAndDeleteLobbyRuntime(final Long lobbyId, final Long runtimeId) {
        return findLobbyRuntime(lobbyId, runtimeId)
                .flatMap(lobbyRuntime -> deleteLobbyRuntime(lobbyId, lobbyRuntime.getId()));
    }
}
