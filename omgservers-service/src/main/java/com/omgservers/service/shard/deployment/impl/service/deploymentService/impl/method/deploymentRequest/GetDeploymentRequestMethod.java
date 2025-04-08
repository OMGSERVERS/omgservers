package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentRequest.GetDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.GetDeploymentRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentRequestMethod {
    Uni<GetDeploymentRequestResponse> execute(ShardModel shardModel, GetDeploymentRequestRequest request);
}
