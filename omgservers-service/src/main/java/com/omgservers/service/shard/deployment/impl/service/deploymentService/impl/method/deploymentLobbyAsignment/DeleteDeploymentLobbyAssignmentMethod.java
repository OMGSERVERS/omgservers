package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentLobbyAssignmentMethod {
    Uni<DeleteDeploymentLobbyAssignmentResponse> execute(DeleteDeploymentLobbyAssignmentRequest request);
}
