package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.countRuntimeAssignments;

import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface CountRuntimeAssignmentsMethod {
    Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(CountRuntimeAssignmentsRequest request);
}
