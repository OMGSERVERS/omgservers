package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.module.deployment.deploymentCommand.ViewDeploymentCommandsRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.ViewDeploymentCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentCommandsMethod {
    Uni<ViewDeploymentCommandsResponse> execute(ViewDeploymentCommandsRequest request);
}
