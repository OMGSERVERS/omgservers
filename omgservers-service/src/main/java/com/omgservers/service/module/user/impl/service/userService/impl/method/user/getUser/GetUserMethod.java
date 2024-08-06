package com.omgservers.service.module.user.impl.service.userService.impl.method.user.getUser;

import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import io.smallrye.mutiny.Uni;

public interface GetUserMethod {
    Uni<GetUserResponse> getUser(GetUserRequest request);
}
