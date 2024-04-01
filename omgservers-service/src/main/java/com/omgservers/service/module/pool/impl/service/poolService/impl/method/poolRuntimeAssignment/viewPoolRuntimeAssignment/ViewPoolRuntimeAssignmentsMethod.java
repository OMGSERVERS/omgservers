package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.viewPoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.ViewPoolRuntimeAssignmentsRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.ViewPoolRuntimeAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolRuntimeAssignmentsMethod {
    Uni<ViewPoolRuntimeAssignmentsResponse> viewPoolRuntimeAssignments(ViewPoolRuntimeAssignmentsRequest request);
}
