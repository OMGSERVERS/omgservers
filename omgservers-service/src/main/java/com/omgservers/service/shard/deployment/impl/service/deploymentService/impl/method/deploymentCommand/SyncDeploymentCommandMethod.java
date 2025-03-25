package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentCommandMethod {
    Uni<SyncDeploymentCommandResponse> execute(SyncDeploymentCommandRequest request);
}
