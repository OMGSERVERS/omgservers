package com.omgservers.service.shard.lobby.impl.service.lobbyService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface LobbyService {
    Uni<GetLobbyResponse> getLobby(@Valid GetLobbyRequest request);

    Uni<SyncLobbyResponse> syncLobby(@Valid SyncLobbyRequest request);

    Uni<SyncLobbyResponse> syncLobbyWithIdempotency(@Valid SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> deleteLobby(@Valid DeleteLobbyRequest request);

    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(@Valid GetLobbyRuntimeRefRequest request);

    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(@Valid FindLobbyRuntimeRefRequest request);

    Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(@Valid SyncLobbyRuntimeRefRequest request);

    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(@Valid DeleteLobbyRuntimeRefRequest request);
}
