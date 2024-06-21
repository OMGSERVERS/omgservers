package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.getRuntimeAssignment;

import com.omgservers.model.dto.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeAssignmentMethod {
    Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(GetRuntimeAssignmentRequest request);
}
