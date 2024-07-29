package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.getMatchmakerAssignment;

import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerAssignmentMethod {
    Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(GetMatchmakerAssignmentRequest request);
}
