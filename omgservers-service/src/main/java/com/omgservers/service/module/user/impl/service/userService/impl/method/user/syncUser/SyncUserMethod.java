package com.omgservers.service.module.user.impl.service.userService.impl.method.user.syncUser;

import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserResponse> syncUser(SyncUserRequest request);
}
