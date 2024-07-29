package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.viewRuntimePermissions;

import com.omgservers.schema.module.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimePermissionsMethod {
    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(ViewRuntimePermissionsRequest request);
}
