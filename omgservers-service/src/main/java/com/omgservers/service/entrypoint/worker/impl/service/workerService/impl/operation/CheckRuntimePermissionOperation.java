package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.operation;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import io.smallrye.mutiny.Uni;

public interface CheckRuntimePermissionOperation {
    Uni<Void> checkRuntimePermission(final Long runtimeId,
                                     final Long userId,
                                     final RuntimePermissionEnum permission);
}
