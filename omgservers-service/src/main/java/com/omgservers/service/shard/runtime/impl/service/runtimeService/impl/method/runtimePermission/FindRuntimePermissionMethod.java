package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission;

import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.FindRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimePermissionMethod {
    Uni<FindRuntimePermissionResponse> execute(FindRuntimePermissionRequest request);
}
