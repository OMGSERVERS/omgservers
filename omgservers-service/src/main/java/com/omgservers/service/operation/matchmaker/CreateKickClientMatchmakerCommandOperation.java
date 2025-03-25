package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface CreateKickClientMatchmakerCommandOperation {
    Uni<Boolean> execute(Long matchmakerId,
                         Long matchId,
                         Long clientId);
}
