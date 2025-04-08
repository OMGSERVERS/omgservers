package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchAssignmentMethod {
    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(ShardModel shardModel,
                                                         DeleteMatchmakerMatchAssignmentRequest request);
}
