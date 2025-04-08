package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.schema.module.user.SyncPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPlayerMethod {
    Uni<SyncPlayerResponse> syncPlayer(ShardModel shardModel, SyncPlayerRequest request);
}
