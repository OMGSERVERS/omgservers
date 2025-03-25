package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentMatchmakerResourceMethod {
    Uni<SyncDeploymentMatchmakerResourceResponse> execute(SyncDeploymentMatchmakerResourceRequest request);
}
