package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchesOperation {
    Uni<Void> execute(Long matchmakerId);
}
