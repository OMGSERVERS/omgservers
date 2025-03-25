package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState;

import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateDeploymentStateMethod {

    Uni<UpdateDeploymentStateResponse> execute(UpdateDeploymentStateRequest request);
}
