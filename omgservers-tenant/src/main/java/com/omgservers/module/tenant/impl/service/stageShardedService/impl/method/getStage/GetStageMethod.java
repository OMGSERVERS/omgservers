package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.getStage;

import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageInternalResponse> getStage(GetStageShardRequest request);
}
