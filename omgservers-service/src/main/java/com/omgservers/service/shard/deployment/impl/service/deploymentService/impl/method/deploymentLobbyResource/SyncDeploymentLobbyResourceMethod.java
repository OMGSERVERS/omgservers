package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentLobbyResourceMethod {
    Uni<SyncDeploymentLobbyResourceResponse> execute(ShardModel shardModel, SyncDeploymentLobbyResourceRequest request);
}
