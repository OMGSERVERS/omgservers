package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerAssignmentMethod {
    Uni<GetMatchmakerAssignmentResponse> execute(GetMatchmakerAssignmentRequest request);
}
