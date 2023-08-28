package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProject;

import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectShardedResponse> syncProject(SyncProjectShardedRequest request);
}
