package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentRequest.DeleteDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.DeleteDeploymentRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentRequestMethod {
    Uni<DeleteDeploymentRequestResponse> execute(ShardModel shardModel, DeleteDeploymentRequestRequest request);
}
