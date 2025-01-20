package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface CountRuntimeAssignmentsMethod {
    Uni<CountRuntimeAssignmentsResponse> execute(CountRuntimeAssignmentsRequest request);
}
