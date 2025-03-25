package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface CreateCloseMatchMatchmakerCommandOperation {
    Uni<Boolean> executeFailSafe(Long matchmakerId,
                                 Long matchId);
}
