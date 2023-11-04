package com.omgservers.module.tenant.impl.service.stageService.impl.method.syncStagePermission;

import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStagePermissionMethod {
    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);
}