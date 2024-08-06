package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.viewProjectPermissions;

import com.omgservers.schema.module.tenant.ViewProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewProjectPermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewProjectPermissionsMethod {
    Uni<ViewProjectPermissionsResponse> viewProjectPermissions(ViewProjectPermissionsRequest request);
}
