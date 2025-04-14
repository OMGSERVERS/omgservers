package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.SyncDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.SyncDeploymentLobbyAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentLobbyAssignmentMethod {
    Uni<SyncDeploymentLobbyAssignmentResponse> execute(ShardModel shardModel,
                                                       SyncDeploymentLobbyAssignmentRequest request);
}
