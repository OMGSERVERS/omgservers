package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.module.deployment.deploymentCommand.DeleteDeploymentCommandRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.DeleteDeploymentCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentCommandMethod {
    Uni<DeleteDeploymentCommandResponse> execute(DeleteDeploymentCommandRequest request);
}
