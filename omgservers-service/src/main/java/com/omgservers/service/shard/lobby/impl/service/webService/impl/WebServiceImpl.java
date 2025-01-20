package com.omgservers.service.shard.lobby.impl.service.webService.impl;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.LobbyService;
import com.omgservers.service.shard.lobby.impl.service.webService.WebService;
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
