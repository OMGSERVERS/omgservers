package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityRequest;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateRuntimeAssignmentLastActivityMethod {
    Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(UpdateRuntimeAssignmentLastActivityRequest request);
}
