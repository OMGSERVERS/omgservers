package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeAssignmentMethod {
    Uni<DeleteRuntimeAssignmentResponse> execute(ShardModel shardModel, DeleteRuntimeAssignmentRequest request);
}
