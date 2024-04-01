package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.deletePoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.DeletePoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.DeletePoolRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolRuntimeAssignmentMethod {
    Uni<DeletePoolRuntimeAssignmentResponse> deletePoolRuntimeAssignment(DeletePoolRuntimeAssignmentRequest request);
}
