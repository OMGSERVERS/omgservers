package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentMatchmakerResourceMethod {
    Uni<GetDeploymentMatchmakerResourceResponse> execute(GetDeploymentMatchmakerResourceRequest request);
}
