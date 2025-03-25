package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentCommandsOperation {
    Uni<Void> execute(Long deploymentId);
}
