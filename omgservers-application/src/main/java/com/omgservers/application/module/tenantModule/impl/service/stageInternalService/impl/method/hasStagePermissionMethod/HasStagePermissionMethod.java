package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.hasStagePermissionMethod;

import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface HasStagePermissionMethod {
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request);
}
