package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod;

import com.omgservers.dto.tenantModule.SyncStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStagePermissionMethod {
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);
}
