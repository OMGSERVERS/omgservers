package com.omgservers.service.server.task.impl.method.executeStageTask.dto;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public record FetchStageResult(TenantStageModel tenantStage,
                               List<TenantDeploymentResourceModel> tenantDeploymentResources) {
}
