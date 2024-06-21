package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.viewRuntimePermissions;

import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimePermissionsMethod {
    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(ViewRuntimePermissionsRequest request);
}
