package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentMatchmakerResourceMethod {
    Uni<GetDeploymentMatchmakerResourceResponse> execute(ShardModel shardModel,
                                                         GetDeploymentMatchmakerResourceRequest request);
}
