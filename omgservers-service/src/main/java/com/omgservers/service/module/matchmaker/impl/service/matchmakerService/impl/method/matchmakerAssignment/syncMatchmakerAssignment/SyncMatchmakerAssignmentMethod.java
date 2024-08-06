package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.syncMatchmakerAssignment;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerAssignmentMethod {
    Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(SyncMatchmakerAssignmentRequest request);
}
