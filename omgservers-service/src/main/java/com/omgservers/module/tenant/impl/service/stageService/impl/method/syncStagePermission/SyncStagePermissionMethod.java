package com.omgservers.module.tenant.impl.service.stageService.impl.method.syncStagePermission;

import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStagePermissionMethod {
    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);
}
