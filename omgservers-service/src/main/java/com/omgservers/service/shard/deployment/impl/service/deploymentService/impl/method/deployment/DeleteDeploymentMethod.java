package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deployment.DeleteDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.DeleteDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentMethod {
    Uni<DeleteDeploymentResponse> execute(ShardModel shardModel, DeleteDeploymentRequest request);
}
