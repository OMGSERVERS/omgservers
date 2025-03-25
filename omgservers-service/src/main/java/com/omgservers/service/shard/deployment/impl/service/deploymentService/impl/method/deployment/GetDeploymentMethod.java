package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentMethod {
    Uni<GetDeploymentResponse> execute(GetDeploymentRequest request);
}
