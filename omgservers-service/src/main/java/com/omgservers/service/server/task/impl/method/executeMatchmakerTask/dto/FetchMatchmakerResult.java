package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public record FetchMatchmakerResult(Long matchmakerId,
                                    DeploymentMatchmakerResourceModel deploymentMatchmakerResource,
                                    TenantVersionConfigDto tenantVersionConfig,
                                    DeploymentConfigDto deploymentConfig,
                                    MatchmakerStateDto matchmakerState) {
}
