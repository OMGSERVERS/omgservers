package com.omgservers.service.module.user.impl.service.userService.impl.method.user.deleteUser;

import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteUserMethod {
    Uni<DeleteUserResponse> deleteUser(DeleteUserRequest request);
}
