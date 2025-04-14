package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteUserMethod {
    Uni<DeleteUserResponse> deleteUser(ShardModel shardModel, DeleteUserRequest request);
}
