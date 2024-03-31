package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.syncRuntimeAssignment;

import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeAssignmentMethod {
    Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(SyncRuntimeAssignmentRequest request);
}
