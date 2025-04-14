package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentLobbyAssignmentMethod {
    Uni<DeleteDeploymentLobbyAssignmentResponse> execute(ShardModel shardModel,
                                                         DeleteDeploymentLobbyAssignmentRequest request);
}
