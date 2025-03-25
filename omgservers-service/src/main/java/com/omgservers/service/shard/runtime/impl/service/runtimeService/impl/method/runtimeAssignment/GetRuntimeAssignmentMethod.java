package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeAssignmentMethod {
    Uni<GetRuntimeAssignmentResponse> execute(GetRuntimeAssignmentRequest request);
}
