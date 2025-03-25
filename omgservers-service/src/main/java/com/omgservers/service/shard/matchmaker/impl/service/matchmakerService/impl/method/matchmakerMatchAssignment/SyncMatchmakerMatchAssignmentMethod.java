package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchAssignmentMethod {
    Uni<SyncMatchmakerMatchAssignmentResponse> execute(SyncMatchmakerMatchAssignmentRequest request);
}
