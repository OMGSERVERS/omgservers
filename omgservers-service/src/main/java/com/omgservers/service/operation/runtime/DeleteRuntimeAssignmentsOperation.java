package com.omgservers.service.operation.runtime;

import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeAssignmentsOperation {
    Uni<Void> execute(Long runtimeId);
}
