package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.hasProjectPermission;

import com.omgservers.schema.module.tenant.HasProjectPermissionRequest;
import com.omgservers.schema.module.tenant.HasProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);
}
