package com.omgservers.service.operation.deleteDanglingLobbies;

import io.smallrye.mutiny.Uni;

public interface DeleteDanglingLobbiesOperation {
    Uni<Void> execute(Long tenantId, Long deploymentId);
}
