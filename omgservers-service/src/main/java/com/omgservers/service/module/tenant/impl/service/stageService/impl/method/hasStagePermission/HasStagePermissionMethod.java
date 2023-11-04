package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.hasStagePermission;

import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);
}
