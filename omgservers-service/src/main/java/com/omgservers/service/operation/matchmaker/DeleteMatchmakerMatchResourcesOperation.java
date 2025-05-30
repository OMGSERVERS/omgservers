package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchResourcesOperation {
    Uni<Void> execute(Long matchmakerId);
}
