package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerAssignmentMethod {
    Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(DeleteMatchmakerAssignmentRequest request);
}
