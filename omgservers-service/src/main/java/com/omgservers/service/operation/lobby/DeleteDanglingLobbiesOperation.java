package com.omgservers.service.operation.lobby;

import io.smallrye.mutiny.Uni;

public interface DeleteDanglingLobbiesOperation {
    Uni<Void> execute(Long tenantId, Long deploymentId);
}
