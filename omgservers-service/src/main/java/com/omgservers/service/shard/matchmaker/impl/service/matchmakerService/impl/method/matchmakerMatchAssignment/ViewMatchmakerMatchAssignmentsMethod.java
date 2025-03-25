package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerMatchAssignmentsMethod {
    Uni<ViewMatchmakerMatchAssignmentsResponse> execute(ViewMatchmakerMatchAssignmentsRequest request);
}
