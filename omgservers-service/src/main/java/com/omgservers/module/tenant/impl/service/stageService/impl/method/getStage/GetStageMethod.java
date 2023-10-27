package com.omgservers.module.tenant.impl.service.stageService.impl.method.getStage;

import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageResponse> getStage(GetStageRequest request);
}
