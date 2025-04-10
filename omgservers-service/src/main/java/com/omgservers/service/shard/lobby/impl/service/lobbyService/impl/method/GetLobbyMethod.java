package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import io.smallrye.mutiny.Uni;

public interface GetLobbyMethod {
    Uni<GetLobbyResponse> execute(ShardModel shardModel, GetLobbyRequest request);
}
