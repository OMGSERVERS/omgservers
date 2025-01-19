package com.omgservers.service.operation.assignMatchmaker;

import io.smallrye.mutiny.Uni;

public interface AssignMatchmakerOperation {
    Uni<Boolean> execute(Long clientId, Long matchmakerId, String idempotencyKey);
}
