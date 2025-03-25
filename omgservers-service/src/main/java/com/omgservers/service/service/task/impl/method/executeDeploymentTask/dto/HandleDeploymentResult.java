package com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;

public record HandleDeploymentResult(Long deploymentId,
                                     DeploymentChangeOfStateDto deploymentChangeOfState) {
}
