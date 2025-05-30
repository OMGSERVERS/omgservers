package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchAssignmentsOperation {
    Uni<Void> execute(Long matchmakerId);
}
