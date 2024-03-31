package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.viewRuntimeAssignments;

import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeAssignmentsMethod {
    Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(ViewRuntimeAssignmentsRequest request);
}
