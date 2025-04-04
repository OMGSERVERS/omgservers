package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission;

import com.omgservers.schema.module.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimePermissionMethod {
    Uni<DeleteRuntimePermissionResponse> execute(DeleteRuntimePermissionRequest request);
}
