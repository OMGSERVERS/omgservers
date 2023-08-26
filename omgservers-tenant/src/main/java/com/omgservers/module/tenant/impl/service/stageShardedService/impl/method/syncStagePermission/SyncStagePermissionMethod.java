package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStagePermission;

import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStagePermissionMethod {
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request);
}
