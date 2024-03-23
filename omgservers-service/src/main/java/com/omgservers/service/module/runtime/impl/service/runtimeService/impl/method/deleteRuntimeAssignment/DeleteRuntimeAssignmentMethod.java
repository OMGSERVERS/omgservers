package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeAssignment;

import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeAssignmentMethod {
    Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(DeleteRuntimeAssignmentRequest request);
}
