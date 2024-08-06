package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.findRuntimePermission;

import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.FindRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimePermissionMethod {
    Uni<FindRuntimePermissionResponse> findRuntimePermission(FindRuntimePermissionRequest request);
}
