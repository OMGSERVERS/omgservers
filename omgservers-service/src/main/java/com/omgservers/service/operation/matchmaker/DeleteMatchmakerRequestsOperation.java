package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerRequestsOperation {
    Uni<Void> execute(Long matchmakerId);
}
