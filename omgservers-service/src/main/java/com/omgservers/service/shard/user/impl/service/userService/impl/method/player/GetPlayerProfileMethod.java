package com.omgservers.service.shard.user.impl.service.userService.impl.method.player;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.GetPlayerProfileRequest;
import com.omgservers.schema.shard.user.GetPlayerProfileResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerProfileMethod {
    Uni<GetPlayerProfileResponse> getPlayerProfile(ShardModel shardModel, GetPlayerProfileRequest request);
}
