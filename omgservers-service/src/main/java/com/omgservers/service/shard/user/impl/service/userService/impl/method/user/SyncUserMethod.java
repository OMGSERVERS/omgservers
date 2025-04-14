package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.schema.shard.user.SyncUserResponse;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserResponse> syncUser(ShardModel shardModel, SyncUserRequest request);
}
