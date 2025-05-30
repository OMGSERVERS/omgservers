package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.GetPlayerRequest;
import com.omgservers.schema.shard.user.GetPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerResponse> getPlayer(ShardModel shardModel, GetPlayerRequest request);
}
