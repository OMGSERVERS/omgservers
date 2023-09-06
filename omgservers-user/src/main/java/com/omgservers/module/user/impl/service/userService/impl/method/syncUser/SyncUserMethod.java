package com.omgservers.module.user.impl.service.userService.impl.method.syncUser;

import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.SyncUserShardedRequest;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserShardedResponse> syncUser(SyncUserShardedRequest request);
}
