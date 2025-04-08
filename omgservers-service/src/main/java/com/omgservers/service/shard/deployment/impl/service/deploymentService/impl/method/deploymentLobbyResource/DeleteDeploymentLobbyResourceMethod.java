package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.DeleteDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.DeleteDeploymentLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentLobbyResourceMethod {
    Uni<DeleteDeploymentLobbyResourceResponse> execute(ShardModel shardModel, DeleteDeploymentLobbyResourceRequest request);
}
