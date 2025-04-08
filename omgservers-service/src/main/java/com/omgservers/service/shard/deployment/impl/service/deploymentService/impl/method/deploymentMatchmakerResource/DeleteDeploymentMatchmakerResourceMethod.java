package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentMatchmakerResourceMethod {
    Uni<DeleteDeploymentMatchmakerResourceResponse> execute(ShardModel shardModel,
                                                            DeleteDeploymentMatchmakerResourceRequest request);
}
