package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchAssignmentMethod {
    Uni<SyncMatchmakerMatchAssignmentResponse> execute(ShardModel shardModel,
                                                       SyncMatchmakerMatchAssignmentRequest request);
}
