package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchAssignmentMethod {
    Uni<DeleteMatchmakerMatchAssignmentResponse> execute(DeleteMatchmakerMatchAssignmentRequest request);
}
