package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.viewMatchmakerAssignments;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerAssignmentsMethod {
    Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(ViewMatchmakerAssignmentsRequest request);
}
