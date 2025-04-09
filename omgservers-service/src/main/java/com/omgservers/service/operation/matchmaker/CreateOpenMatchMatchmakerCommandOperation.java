package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface CreateOpenMatchMatchmakerCommandOperation {
    Uni<Boolean> execute(Long matchmakerId,
                         Long matchId);

    Uni<Boolean> executeFailSafe(Long matchmakerId,
                                 Long matchId);
}
