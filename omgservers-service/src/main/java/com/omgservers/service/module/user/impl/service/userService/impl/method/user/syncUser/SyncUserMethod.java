package com.omgservers.service.module.user.impl.service.userService.impl.method.user.syncUser;

import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserResponse> syncUser(SyncUserRequest request);
}
