package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface CreateKickClientMatchmakerCommandOperation {
    Uni<Boolean> executeFailSafe(Long matchmakerId,
                                 Long matchId,
                                 Long clientId);
}
