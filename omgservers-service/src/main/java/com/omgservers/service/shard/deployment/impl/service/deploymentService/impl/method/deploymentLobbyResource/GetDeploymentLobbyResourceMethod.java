package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentLobbyResourceMethod {
    Uni<GetDeploymentLobbyResourceResponse> execute(ShardModel shardModel, GetDeploymentLobbyResourceRequest request);
}
