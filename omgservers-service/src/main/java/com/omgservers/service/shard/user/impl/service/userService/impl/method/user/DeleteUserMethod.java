package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteUserMethod {
    Uni<DeleteUserResponse> deleteUser(ShardModel shardModel, DeleteUserRequest request);
}
