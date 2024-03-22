package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerAssignments;

import com.omgservers.model.dto.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerAssignmentsMethod {
    Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(ViewMatchmakerAssignmentsRequest request);
}
