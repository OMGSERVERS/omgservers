package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProjectPermission;

import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request);
}
