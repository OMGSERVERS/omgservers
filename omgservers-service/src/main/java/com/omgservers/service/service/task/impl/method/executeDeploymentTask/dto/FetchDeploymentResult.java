package com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;

public record FetchDeploymentResult(Long deploymentId,
                                    DeploymentStateDto deploymentState) {
}
