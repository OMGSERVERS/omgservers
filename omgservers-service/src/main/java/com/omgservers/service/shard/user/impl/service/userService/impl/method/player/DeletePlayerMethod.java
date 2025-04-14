package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.DeletePlayerRequest;
import com.omgservers.schema.shard.user.DeletePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerResponse> deletePlayer(ShardModel shardModel, DeletePlayerRequest request);
}
