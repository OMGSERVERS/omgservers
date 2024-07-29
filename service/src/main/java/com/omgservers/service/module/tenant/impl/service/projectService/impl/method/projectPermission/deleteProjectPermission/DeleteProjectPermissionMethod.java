package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.deleteProjectPermission;

import com.omgservers.schema.module.tenant.DeleteProjectPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectPermissionMethod {
    Uni<DeleteProjectPermissionResponse> deleteProjectPermission(DeleteProjectPermissionRequest request);
}
