package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimePermission;

import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimePermissionMethod {
    Uni<FindRuntimePermissionResponse> findRuntimePermission(FindRuntimePermissionRequest request);
}
