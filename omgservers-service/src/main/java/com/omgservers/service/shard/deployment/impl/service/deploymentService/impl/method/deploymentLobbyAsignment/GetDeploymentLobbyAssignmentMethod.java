package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentLobbyAssignmentMethod {
    Uni<GetDeploymentLobbyAssignmentResponse> execute(ShardModel shardModel,
                                                      GetDeploymentLobbyAssignmentRequest request);
}
