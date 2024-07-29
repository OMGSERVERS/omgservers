package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.deleteMatchmakerAssignment;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerAssignmentMethod {
    Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(DeleteMatchmakerAssignmentRequest request);
}
