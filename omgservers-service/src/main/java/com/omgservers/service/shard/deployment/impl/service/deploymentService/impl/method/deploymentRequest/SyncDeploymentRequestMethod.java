package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.module.deployment.deploymentRequest.SyncDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.SyncDeploymentRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentRequestMethod {
    Uni<SyncDeploymentRequestResponse> execute(SyncDeploymentRequestRequest request);
}
