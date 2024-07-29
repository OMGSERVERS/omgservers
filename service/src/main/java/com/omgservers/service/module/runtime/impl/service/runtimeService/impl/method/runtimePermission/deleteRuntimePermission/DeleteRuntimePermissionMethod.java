package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.deleteRuntimePermission;

import com.omgservers.schema.module.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimePermissionMethod {
    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(DeleteRuntimePermissionRequest request);
}
