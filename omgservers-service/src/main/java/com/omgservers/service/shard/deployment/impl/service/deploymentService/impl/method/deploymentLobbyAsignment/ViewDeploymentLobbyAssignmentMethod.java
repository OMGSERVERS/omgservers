package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentLobbyAssignmentMethod {
    Uni<ViewDeploymentLobbyAssignmentsResponse> execute(ShardModel shardModel, ViewDeploymentLobbyAssignmentsRequest request);
}
