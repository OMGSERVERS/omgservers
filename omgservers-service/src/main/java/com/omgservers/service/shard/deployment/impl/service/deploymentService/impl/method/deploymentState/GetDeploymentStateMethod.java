package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.module.deployment.deploymentState.GetDeploymentStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentStateMethod {
    Uni<GetDeploymentStateResponse> execute(ShardModel shardModel, GetDeploymentStateRequest request);
}
