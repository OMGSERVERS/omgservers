package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeAssignment;

import com.omgservers.model.dto.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimeAssignmentMethod {
    Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(FindRuntimeAssignmentRequest request);
}
