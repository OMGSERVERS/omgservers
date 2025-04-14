package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchAssignmentsMethod {
    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ShardModel shardModel,
                                                        ViewMatchmakerMatchAssignmentsRequest request);
}
