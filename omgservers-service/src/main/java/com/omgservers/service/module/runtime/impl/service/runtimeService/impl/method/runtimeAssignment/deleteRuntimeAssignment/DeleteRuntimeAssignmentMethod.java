package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.deleteRuntimeAssignment;

import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeAssignmentMethod {
    Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(DeleteRuntimeAssignmentRequest request);
}
