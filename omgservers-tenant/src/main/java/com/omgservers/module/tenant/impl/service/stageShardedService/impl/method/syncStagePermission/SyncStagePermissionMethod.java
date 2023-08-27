package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStagePermission;

import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStagePermissionMethod {
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardedRequest request);
}
