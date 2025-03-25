package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public record FetchMatchmakerResult(Long matchmakerId,
                                    TenantVersionConfigDto tenantVersionConfig,
                                    MatchmakerStateDto matchmakerState) {
}
