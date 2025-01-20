package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerMatchAssignmentMethod {
    Uni<FindMatchmakerMatchAssignmentResponse> execute(FindMatchmakerMatchAssignmentRequest request);
}
