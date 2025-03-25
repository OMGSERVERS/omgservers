package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentMatchmakerAssignmentMethod {
    Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(ViewDeploymentMatchmakerAssignmentsRequest request);
}
