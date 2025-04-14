package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentMethod {
    Uni<SyncDeploymentResponse> execute(ShardModel shardModel, SyncDeploymentRequest request);
}
