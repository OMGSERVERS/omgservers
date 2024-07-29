package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteStagePermissions;

import com.omgservers.schema.entrypoint.support.DeleteStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteStagePermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStagePermissionsMethod {
    Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(
            DeleteStagePermissionsSupportRequest request);
}
