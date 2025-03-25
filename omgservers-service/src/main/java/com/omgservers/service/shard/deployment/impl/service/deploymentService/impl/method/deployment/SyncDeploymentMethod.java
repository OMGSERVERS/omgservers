package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.module.deployment.deployment.SyncDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.SyncDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentMethod {
    Uni<SyncDeploymentResponse> execute(SyncDeploymentRequest request);
}
