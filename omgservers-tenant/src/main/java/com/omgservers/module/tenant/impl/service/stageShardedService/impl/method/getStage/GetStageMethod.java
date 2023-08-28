package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.getStage;

import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageShardedResponse> getStage(GetStageShardedRequest request);
}
