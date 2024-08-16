package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.operation;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import io.smallrye.mutiny.Uni;

public interface CheckRuntimePermissionOperation {
    Uni<Void> checkRuntimePermission(final Long runtimeId,
                                     final Long userId,
                                     final RuntimePermissionEnum permission);
}
