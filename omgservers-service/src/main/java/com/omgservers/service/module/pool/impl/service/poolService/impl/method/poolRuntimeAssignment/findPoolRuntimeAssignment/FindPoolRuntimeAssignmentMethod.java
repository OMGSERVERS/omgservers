package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.findPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.FindPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.FindPoolRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRuntimeAssignmentMethod {
    Uni<FindPoolRuntimeAssignmentResponse> findPoolRuntimeAssignment(FindPoolRuntimeAssignmentRequest request);
}
