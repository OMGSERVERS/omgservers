package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentRequest.SyncDeploymentRequestRequest;
import com.omgservers.schema.shard.deployment.deploymentRequest.SyncDeploymentRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentRequestMethod {
    Uni<SyncDeploymentRequestResponse> execute(ShardModel shardModel, SyncDeploymentRequestRequest request);
}
