package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentRequest.ViewDeploymentRequestsRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.ViewDeploymentRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentRequestsMethod {
    Uni<ViewDeploymentRequestsResponse> execute(ShardModel shardModel, ViewDeploymentRequestsRequest request);
}
