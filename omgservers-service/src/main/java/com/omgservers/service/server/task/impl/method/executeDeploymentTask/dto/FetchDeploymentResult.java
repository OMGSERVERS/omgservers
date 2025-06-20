package com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record FetchDeploymentResult(Long deploymentId,
                                    TenantDeploymentResourceModel tenantDeploymentResource,
                                    TenantVersionConfigDto tenantVersionConfig,
                                    DeploymentStateDto deploymentState) {
}
