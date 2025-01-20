package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.getLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetLobbyRuntimeRefMethod {
    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(GetLobbyRuntimeRefRequest request);
}
