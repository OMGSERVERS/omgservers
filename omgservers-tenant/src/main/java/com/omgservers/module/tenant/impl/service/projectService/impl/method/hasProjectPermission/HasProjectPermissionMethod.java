package com.omgservers.module.tenant.impl.service.projectService.impl.method.hasProjectPermission;

import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);
}
