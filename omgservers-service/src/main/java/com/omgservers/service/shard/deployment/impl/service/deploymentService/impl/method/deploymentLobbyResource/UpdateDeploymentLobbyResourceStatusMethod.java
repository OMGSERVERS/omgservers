package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateDeploymentLobbyResourceStatusMethod {
    Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(ShardModel shardModel, UpdateDeploymentLobbyResourceStatusRequest request);
}
