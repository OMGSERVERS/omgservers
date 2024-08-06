package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createStagePermissions;

import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateStagePermissionsMethod {
    Uni<CreateStagePermissionsSupportResponse> createStagePermissions(
            CreateStagePermissionsSupportRequest request);
}
