package com.omgservers.service.shard.lobby.impl.service.webService;

import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import com.omgservers.schema.shard.lobby.GetLobbyRequest;
import com.omgservers.schema.shard.lobby.GetLobbyResponse;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetLobbyResponse> execute(GetLobbyRequest request);

    Uni<SyncLobbyResponse> execute(SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> execute(DeleteLobbyRequest request);
}
