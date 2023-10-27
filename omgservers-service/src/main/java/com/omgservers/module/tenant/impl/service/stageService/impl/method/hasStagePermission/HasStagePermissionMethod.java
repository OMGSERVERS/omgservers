package com.omgservers.module.tenant.impl.service.stageService.impl.method.hasStagePermission;

import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);
}
