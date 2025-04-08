package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import io.smallrye.mutiny.Uni;

public interface GetUserMethod {
    Uni<GetUserResponse> getUser(ShardModel shardModel, GetUserRequest request);
}
