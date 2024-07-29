package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProjectPermissions;

import com.omgservers.schema.entrypoint.support.CreateProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectPermissionsMethod {
    Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            CreateProjectPermissionsSupportRequest request);
}
