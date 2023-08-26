package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.hasStagePermission;

import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request);
}
