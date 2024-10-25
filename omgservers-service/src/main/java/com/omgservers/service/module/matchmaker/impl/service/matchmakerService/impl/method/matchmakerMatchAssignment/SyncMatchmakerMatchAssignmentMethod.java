package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerMatchAssignmentMethod {
    Uni<SyncMatchmakerMatchAssignmentResponse> execute(SyncMatchmakerMatchAssignmentRequest request);
}
