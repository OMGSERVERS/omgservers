package com.omgservers.module.user.impl.service.objectShardedService.impl.method.syncObject;

import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import io.smallrye.mutiny.Uni;

public interface SyncObjectMethod {
    Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request);
}
