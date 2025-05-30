package com.omgservers.service.operation.deployment;

import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentMatchmakerAssignmentsOperation {
    Uni<Void> execute(Long deploymentId);
}
