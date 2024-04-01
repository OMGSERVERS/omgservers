package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.syncPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.SyncPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.SyncPoolRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolRuntimeAssignmentMethod {
    Uni<SyncPoolRuntimeAssignmentResponse> syncPoolRuntimeAssignment(SyncPoolRuntimeAssignmentRequest request);
}
