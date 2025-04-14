package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLobbyMethod {
    Uni<SyncLobbyResponse> execute(ShardModel shardModel, SyncLobbyRequest request);
}
