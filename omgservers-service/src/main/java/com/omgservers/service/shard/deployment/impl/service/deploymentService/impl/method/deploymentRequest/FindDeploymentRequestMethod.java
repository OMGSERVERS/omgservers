package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentRequest.FindDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.FindDeploymentRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindDeploymentRequestMethod {
    Uni<FindDeploymentRequestResponse> execute(ShardModel shardModel, FindDeploymentRequestRequest request);
}
