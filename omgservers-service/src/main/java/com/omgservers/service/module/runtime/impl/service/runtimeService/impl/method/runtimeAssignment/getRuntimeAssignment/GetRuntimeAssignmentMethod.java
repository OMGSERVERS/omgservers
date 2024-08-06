package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.getRuntimeAssignment;

import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeAssignmentMethod {
    Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(GetRuntimeAssignmentRequest request);
}
