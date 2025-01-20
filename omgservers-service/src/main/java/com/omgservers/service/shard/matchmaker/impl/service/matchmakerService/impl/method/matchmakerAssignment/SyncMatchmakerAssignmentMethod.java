package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerAssignmentMethod {
    Uni<SyncMatchmakerAssignmentResponse> execute(SyncMatchmakerAssignmentRequest request);
}
