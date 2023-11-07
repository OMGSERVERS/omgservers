package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import io.smallrye.mutiny.Uni;

public interface CheckRuntimePermissionOperation {
    Uni<Void> checkRuntimePermission(final Long runtimeId,
                                     final Long userId,
                                     final RuntimePermissionEnum permission);
}
