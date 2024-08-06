package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.deleteStagePermission;

import com.omgservers.schema.module.tenant.DeleteStagePermissionRequest;
import com.omgservers.schema.module.tenant.DeleteStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStagePermissionMethod {
    Uni<DeleteStagePermissionResponse> deleteStagePermission(DeleteStagePermissionRequest request);
}
