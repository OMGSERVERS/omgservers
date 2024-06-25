package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProjectPermissions;

import com.omgservers.model.dto.support.CreateProjectPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateProjectPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectPermissionsMethod {
    Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            CreateProjectPermissionsSupportRequest request);
}
