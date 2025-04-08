package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.FindDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.FindDeploymentMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface FindDeploymentMatchmakerResourceMethod {
    Uni<FindDeploymentMatchmakerResourceResponse> execute(ShardModel shardModel,
                                                          FindDeploymentMatchmakerResourceRequest request);
}
