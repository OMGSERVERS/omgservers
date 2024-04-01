package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.getPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.GetPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.GetPoolRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolRuntimeAssignmentMethod {
    Uni<GetPoolRuntimeAssignmentResponse> getPoolRuntimeAssignment(GetPoolRuntimeAssignmentRequest request);
}
