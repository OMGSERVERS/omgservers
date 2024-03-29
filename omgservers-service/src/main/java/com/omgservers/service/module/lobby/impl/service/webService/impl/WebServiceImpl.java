package com.omgservers.service.module.lobby.impl.service.webService.impl;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.service.module.lobby.impl.service.lobbyService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final LobbyService lobbyService;

    @Override
    public Uni<GetLobbyResponse> getLobby(final GetLobbyRequest request) {
        return lobbyService.getLobby(request);
    }

    @Override
    public Uni<SyncLobbyResponse> syncLobby(final SyncLobbyRequest request) {
        return lobbyService.syncLobby(request);
    }

    @Override
    public Uni<DeleteLobbyResponse> deleteLobby(final DeleteLobbyRequest request) {
        return lobbyService.deleteLobby(request);
    }

    @Override
    public Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(final GetLobbyRuntimeRefRequest request) {
        return lobbyService.getLobbyRuntimeRef(request);
    }

    @Override
    public Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(final FindLobbyRuntimeRefRequest request) {
        return lobbyService.findLobbyRuntimeRef(request);
    }

    @Override
    public Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(final SyncLobbyRuntimeRefRequest request) {
        return lobbyService.syncLobbyRuntimeRef(request);
    }

    @Override
    public Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(final DeleteLobbyRuntimeRefRequest request) {
        return lobbyService.deleteLobbyRuntimeRef(request);
    }
}
