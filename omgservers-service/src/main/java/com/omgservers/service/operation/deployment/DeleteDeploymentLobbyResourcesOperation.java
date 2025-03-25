package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentLobbyResourcesOperation {
    Uni<Void> execute(Long deploymentId);
}
