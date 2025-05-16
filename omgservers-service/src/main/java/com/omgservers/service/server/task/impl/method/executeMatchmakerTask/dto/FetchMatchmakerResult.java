package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public record FetchMatchmakerResult(Long matchmakerId,
                                    TenantVersionConfigDto tenantVersionConfig,
                                    DeploymentConfigDto deploymentConfig,
                                    MatchmakerStateDto matchmakerState) {
}
