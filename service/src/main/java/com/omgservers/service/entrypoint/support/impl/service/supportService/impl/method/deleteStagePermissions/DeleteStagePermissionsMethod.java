package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteStagePermissions;

import com.omgservers.model.dto.support.DeleteStagePermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteStagePermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStagePermissionsMethod {
    Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(
            DeleteStagePermissionsSupportRequest request);
}
