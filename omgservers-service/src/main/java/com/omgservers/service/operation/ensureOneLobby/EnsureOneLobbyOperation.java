package com.omgservers.service.operation.ensureOneLobby;

import io.smallrye.mutiny.Uni;

public interface EnsureOneLobbyOperation {
    Uni<Void> execute(Long tenantId,
                      Long tenantDeploymentId);
}
