package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchmakerAssignment;

import com.omgservers.model.dto.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerAssignmentMethod {
    Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(FindMatchmakerAssignmentRequest request);
}
