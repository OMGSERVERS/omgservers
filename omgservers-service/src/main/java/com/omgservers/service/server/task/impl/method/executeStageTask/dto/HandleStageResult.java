package com.omgservers.service.server.task.impl.method.executeStageTask.dto;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;

import java.util.List;

public record HandleStageResult(List<TenantDeploymentResourceModel> tenantDeploymentResourcesToClose,
                                List<TenantDeploymentResourceModel> tenantDeploymentResourcesToDelete) {
}
