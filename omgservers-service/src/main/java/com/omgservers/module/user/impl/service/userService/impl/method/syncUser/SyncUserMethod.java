package com.omgservers.module.user.impl.service.userService.impl.method.syncUser;

import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.SyncUserRequest;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserResponse> syncUser(SyncUserRequest request);
}
