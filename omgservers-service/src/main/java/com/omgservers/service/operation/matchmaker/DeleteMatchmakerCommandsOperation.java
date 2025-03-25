package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerCommandsOperation {
    Uni<Void> execute(Long matchmakerId);
}
