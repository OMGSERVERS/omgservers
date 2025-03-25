package com.omgservers.service.shard.lobby.impl.service.lobbyService;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface LobbyService {
    Uni<GetLobbyResponse> execute(@Valid GetLobbyRequest request);

    Uni<SyncLobbyResponse> execute(@Valid SyncLobbyRequest request);

    Uni<SyncLobbyResponse> executeWithIdempotency(@Valid SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> execute(@Valid DeleteLobbyRequest request);
}
