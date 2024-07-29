package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.findRuntimeAssignment;

import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimeAssignmentMethod {
    Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(FindRuntimeAssignmentRequest request);
}
