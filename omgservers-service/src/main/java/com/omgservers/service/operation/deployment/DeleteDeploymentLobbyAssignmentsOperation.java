package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentLobbyAssignmentsOperation {
    Uni<Void> execute(Long deploymentId);
}
