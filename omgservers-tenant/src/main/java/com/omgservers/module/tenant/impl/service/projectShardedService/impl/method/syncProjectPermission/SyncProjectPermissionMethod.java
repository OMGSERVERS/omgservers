package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProjectPermission;

import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionShardedResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request);
}
