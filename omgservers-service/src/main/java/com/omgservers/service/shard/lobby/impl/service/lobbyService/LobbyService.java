package com.omgservers.service.shard.lobby.impl.service.lobbyService;

import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import com.omgservers.schema.shard.lobby.GetLobbyRequest;
import com.omgservers.schema.shard.lobby.GetLobbyResponse;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface LobbyService {
    Uni<GetLobbyResponse> execute(@Valid GetLobbyRequest request);

    Uni<SyncLobbyResponse> execute(@Valid SyncLobbyRequest request);

    Uni<SyncLobbyResponse> executeWithIdempotency(@Valid SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> execute(@Valid DeleteLobbyRequest request);
}
