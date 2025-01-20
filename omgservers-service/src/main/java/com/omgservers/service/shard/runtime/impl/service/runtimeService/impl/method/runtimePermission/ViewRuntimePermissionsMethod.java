package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission;

import com.omgservers.schema.module.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimePermissionsMethod {
    Uni<ViewRuntimePermissionsResponse> execute(ViewRuntimePermissionsRequest request);
}
