package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.countRuntimeAssignments;

import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface CountRuntimeAssignmentsMethod {
    Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(CountRuntimeAssignmentsRequest request);
}
