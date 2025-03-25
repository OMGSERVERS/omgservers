package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentMatchmakerResourcesOperation {
    Uni<Void> execute(Long deploymentId);
}
