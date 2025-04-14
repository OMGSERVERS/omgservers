package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.FindPlayerRequest;
import com.omgservers.schema.shard.user.FindPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPlayerMethod {
    Uni<FindPlayerResponse> findPlayer(ShardModel shardModel, FindPlayerRequest request);
}
