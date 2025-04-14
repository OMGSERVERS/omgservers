package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchAssignmentMethod {
    Uni<GetMatchmakerMatchAssignmentResponse> execute(ShardModel shardModel,
                                                      GetMatchmakerMatchAssignmentRequest request);
}
