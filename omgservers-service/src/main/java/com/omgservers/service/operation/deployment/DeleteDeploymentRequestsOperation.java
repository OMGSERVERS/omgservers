package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentRequestsOperation {
    Uni<Void> execute(Long deploymentId);
}
