package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.module.deployment.deployment.GetDeploymentDataRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentDataMethod {
    Uni<GetDeploymentDataResponse> execute(GetDeploymentDataRequest request);
}
