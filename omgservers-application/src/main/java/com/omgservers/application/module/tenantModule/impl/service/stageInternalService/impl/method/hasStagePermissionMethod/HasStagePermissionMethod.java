package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.hasStagePermissionMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.HasStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);
}
