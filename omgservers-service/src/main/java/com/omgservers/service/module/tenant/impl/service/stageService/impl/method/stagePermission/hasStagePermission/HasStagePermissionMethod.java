package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.hasStagePermission;

import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);
}
