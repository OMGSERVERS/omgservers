package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerMatchAssignmentMethod {
    Uni<FindMatchmakerMatchAssignmentResponse> execute(ShardModel shardModel,
                                                       FindMatchmakerMatchAssignmentRequest request);
}
