package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.getStage;

import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageResponse> getStage(GetStageRequest request);
}
