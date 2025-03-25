package com.omgservers.service.shard.lobby.impl.service.webService.impl;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
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
    public Uni<GetLobbyResponse> execute(final GetLobbyRequest request) {
        return lobbyService.execute(request);
    }

    @Override
    public Uni<SyncLobbyResponse> execute(final SyncLobbyRequest request) {
        return lobbyService.execute(request);
    }

    @Override
    public Uni<DeleteLobbyResponse> execute(final DeleteLobbyRequest request) {
        return lobbyService.execute(request);
    }
}
