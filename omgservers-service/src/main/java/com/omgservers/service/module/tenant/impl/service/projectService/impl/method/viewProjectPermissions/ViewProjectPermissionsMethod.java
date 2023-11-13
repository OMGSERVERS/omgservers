package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.viewProjectPermissions;

import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewProjectPermissionsMethod {
    Uni<ViewProjectPermissionsResponse> viewProjectPermissions(ViewProjectPermissionsRequest request);
}
