package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentDataRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentDataMethod {
    Uni<GetDeploymentDataResponse> execute(ShardModel shardModel, GetDeploymentDataRequest request);
}
