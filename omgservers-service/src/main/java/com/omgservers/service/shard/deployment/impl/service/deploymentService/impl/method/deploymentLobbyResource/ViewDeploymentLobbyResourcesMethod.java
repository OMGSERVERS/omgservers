package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentLobbyResourcesMethod {
    Uni<ViewDeploymentLobbyResourcesResponse> execute(ShardModel shardModel, ViewDeploymentLobbyResourcesRequest request);
}
