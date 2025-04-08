package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.SyncRuntimeAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncRuntimeAssignmentMethod {
    Uni<SyncRuntimeAssignmentResponse> execute(ShardModel shardModel, SyncRuntimeAssignmentRequest request);
}
