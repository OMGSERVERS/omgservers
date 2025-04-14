package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.UpdateDeploymentStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateDeploymentStateMethod {
    Uni<UpdateDeploymentStateResponse> execute(ShardModel shardModel, UpdateDeploymentStateRequest request);
}
