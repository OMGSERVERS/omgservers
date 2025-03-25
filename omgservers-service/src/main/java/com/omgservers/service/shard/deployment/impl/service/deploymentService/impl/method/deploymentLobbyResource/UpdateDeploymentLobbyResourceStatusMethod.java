package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.module.deployment.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateDeploymentLobbyResourceStatusMethod {
    Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(UpdateDeploymentLobbyResourceStatusRequest request);
}
