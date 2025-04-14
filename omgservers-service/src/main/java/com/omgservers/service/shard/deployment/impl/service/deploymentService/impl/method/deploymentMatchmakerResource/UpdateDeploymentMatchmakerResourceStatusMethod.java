package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.UpdateDeploymentMatchmakerResourceStatusRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.UpdateDeploymentMatchmakerResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateDeploymentMatchmakerResourceStatusMethod {
    Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(ShardModel shardModel,
                                                                  UpdateDeploymentMatchmakerResourceStatusRequest request);
}
