package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentMatchmakerResourcesMethod {
    Uni<ViewDeploymentMatchmakerResourcesResponse> execute(ViewDeploymentMatchmakerResourcesRequest request);
}
