package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.FindRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindRuntimeAssignmentMethod {
    Uni<FindRuntimeAssignmentResponse> execute(ShardModel shardModel, FindRuntimeAssignmentRequest request);
}
