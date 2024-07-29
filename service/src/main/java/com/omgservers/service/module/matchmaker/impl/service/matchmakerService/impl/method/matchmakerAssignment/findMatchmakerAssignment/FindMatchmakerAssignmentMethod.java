package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.findMatchmakerAssignment;

import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerAssignmentMethod {
    Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(FindMatchmakerAssignmentRequest request);
}
