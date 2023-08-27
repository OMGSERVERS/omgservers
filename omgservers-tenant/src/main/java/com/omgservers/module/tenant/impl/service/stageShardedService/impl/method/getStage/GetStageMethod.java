package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.getStage;

import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageInternalResponse> getStage(GetStageShardedRequest request);
}
