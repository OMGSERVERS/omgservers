package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.module.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentLobbyResourcesMethod {
    Uni<ViewDeploymentLobbyResourcesResponse> execute(ViewDeploymentLobbyResourcesRequest request);
}
