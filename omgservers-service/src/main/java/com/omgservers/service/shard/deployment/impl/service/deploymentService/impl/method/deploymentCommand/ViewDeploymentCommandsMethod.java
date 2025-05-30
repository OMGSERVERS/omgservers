package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentCommand.ViewDeploymentCommandsRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.ViewDeploymentCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentCommandsMethod {
    Uni<ViewDeploymentCommandsResponse> execute(ShardModel shardModel, ViewDeploymentCommandsRequest request);
}
