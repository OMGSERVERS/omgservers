package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStagePermissionMethod {
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);
}
