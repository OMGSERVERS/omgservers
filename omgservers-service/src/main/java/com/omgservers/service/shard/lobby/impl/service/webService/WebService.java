package com.omgservers.service.shard.lobby.impl.service.webService;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetLobbyResponse> execute(GetLobbyRequest request);

    Uni<SyncLobbyResponse> execute(SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> execute(DeleteLobbyRequest request);
}
