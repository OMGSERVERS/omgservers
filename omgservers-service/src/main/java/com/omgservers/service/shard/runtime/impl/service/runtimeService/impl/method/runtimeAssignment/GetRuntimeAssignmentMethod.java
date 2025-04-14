package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeAssignmentMethod {
    Uni<GetRuntimeAssignmentResponse> execute(ShardModel shardModel, GetRuntimeAssignmentRequest request);
}
