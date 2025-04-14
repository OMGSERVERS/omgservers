package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentMatchmakerResourceMethod {
    Uni<SyncDeploymentMatchmakerResourceResponse> execute(ShardModel shardModel,
                                                          SyncDeploymentMatchmakerResourceRequest request);
}
