package com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;

public record FetchDeploymentResult(Long deploymentId,
                                    DeploymentStateDto deploymentState) {
}
