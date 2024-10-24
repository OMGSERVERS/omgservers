package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeAssignmentMethod {
    Uni<SyncRuntimeAssignmentResponse> execute(SyncRuntimeAssignmentRequest request);
}
