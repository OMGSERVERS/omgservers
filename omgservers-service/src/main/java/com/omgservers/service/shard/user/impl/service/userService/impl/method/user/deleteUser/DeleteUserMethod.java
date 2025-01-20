package com.omgservers.service.shard.user.impl.service.userService.impl.method.user.deleteUser;

import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteUserMethod {
    Uni<DeleteUserResponse> deleteUser(DeleteUserRequest request);
}
