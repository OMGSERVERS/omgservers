package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.FindDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.FindDeploymentLobbyAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindDeploymentLobbyAssignmentMethod {
    Uni<FindDeploymentLobbyAssignmentResponse> execute(ShardModel shardModel,
                                                       FindDeploymentLobbyAssignmentRequest request);
}
