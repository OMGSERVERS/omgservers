package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentMethod {
    Uni<GetDeploymentResponse> execute(ShardModel shardModel, GetDeploymentRequest request);
}
