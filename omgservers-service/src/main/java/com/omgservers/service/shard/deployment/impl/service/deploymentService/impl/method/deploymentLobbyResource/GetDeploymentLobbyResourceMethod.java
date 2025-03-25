package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.module.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentLobbyResourceMethod {
    Uni<GetDeploymentLobbyResourceResponse> execute(GetDeploymentLobbyResourceRequest request);
}
