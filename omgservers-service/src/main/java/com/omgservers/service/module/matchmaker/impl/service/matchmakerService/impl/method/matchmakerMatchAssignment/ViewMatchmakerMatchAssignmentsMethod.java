package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchAssignmentsMethod {
    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request);
}
