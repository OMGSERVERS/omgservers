package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.hasProjectPermission;

import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);
}
