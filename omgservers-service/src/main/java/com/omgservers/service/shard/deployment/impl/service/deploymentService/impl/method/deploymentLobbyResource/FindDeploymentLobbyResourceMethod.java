package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.module.deployment.deploymentLobbyResource.FindDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.FindDeploymentLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface FindDeploymentLobbyResourceMethod {
    Uni<FindDeploymentLobbyResourceResponse> execute(FindDeploymentLobbyResourceRequest request);
}
