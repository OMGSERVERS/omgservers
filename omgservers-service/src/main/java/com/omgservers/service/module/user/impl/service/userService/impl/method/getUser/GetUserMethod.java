package com.omgservers.service.module.user.impl.service.userService.impl.method.getUser;

import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import io.smallrye.mutiny.Uni;

public interface GetUserMethod {
    Uni<GetUserResponse> getUser(GetUserRequest request);
}
