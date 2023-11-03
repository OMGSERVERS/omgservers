package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimePermission;

import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimePermissionMethod {
    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(DeleteRuntimePermissionRequest request);
}
