package com.omgservers.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission;

import io.smallrye.mutiny.Uni;

public interface CheckRuntimePermissionOperation {
    Uni<Void> checkRuntimePermission(final Long runtimeId, final Long userId);
}
