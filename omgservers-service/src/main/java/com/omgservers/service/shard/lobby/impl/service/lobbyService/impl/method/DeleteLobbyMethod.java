package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyMethod {
    Uni<DeleteLobbyResponse> execute(ShardModel shardModel, DeleteLobbyRequest request);
}
