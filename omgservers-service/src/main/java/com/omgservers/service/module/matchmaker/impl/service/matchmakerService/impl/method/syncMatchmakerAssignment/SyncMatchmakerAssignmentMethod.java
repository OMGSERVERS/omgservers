package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerAssignment;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakerAssignmentMethod {
    Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(SyncMatchmakerAssignmentRequest request);
}
