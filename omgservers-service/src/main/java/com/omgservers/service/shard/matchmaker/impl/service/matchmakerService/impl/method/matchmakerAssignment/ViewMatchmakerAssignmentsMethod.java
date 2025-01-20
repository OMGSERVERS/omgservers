package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewMatchmakerAssignmentsMethod {
    Uni<ViewMatchmakerAssignmentsResponse> execute(ViewMatchmakerAssignmentsRequest request);
}
