package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.SyncDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.SyncDeploymentLobbyAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentLobbyAssignmentMethod {
    Uni<SyncDeploymentLobbyAssignmentResponse> execute(SyncDeploymentLobbyAssignmentRequest request);
}
