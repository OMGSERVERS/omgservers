package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentLobbyAssignmentMethod {
    Uni<ViewDeploymentLobbyAssignmentsResponse> execute(ViewDeploymentLobbyAssignmentsRequest request);
}
