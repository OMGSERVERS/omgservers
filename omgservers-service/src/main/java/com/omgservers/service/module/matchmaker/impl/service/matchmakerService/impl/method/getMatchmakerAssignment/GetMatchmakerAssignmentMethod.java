package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerAssignment;

import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerAssignmentMethod {
    Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(GetMatchmakerAssignmentRequest request);
}
