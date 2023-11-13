package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.deleteProjectPermission;

import com.omgservers.model.dto.tenant.DeleteProjectPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectPermissionMethod {
    Uni<DeleteProjectPermissionResponse> deleteProjectPermission(DeleteProjectPermissionRequest request);
}
