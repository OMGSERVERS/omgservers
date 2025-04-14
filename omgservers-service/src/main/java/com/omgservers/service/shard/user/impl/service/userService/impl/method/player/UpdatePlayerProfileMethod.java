package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.shard.user.UpdatePlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerProfileMethod {
    Uni<UpdatePlayerProfileResponse> updatePlayerProfile(ShardModel shardModel, UpdatePlayerProfileRequest request);
}
