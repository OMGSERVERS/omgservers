package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.deleteStagePermission;

import com.omgservers.model.dto.tenant.DeleteStagePermissionRequest;
import com.omgservers.model.dto.tenant.DeleteStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStagePermissionMethod {
    Uni<DeleteStagePermissionResponse> deleteStagePermission(DeleteStagePermissionRequest request);
}
