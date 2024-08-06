package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.getStage;

import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageResponse> getStage(GetStageRequest request);
}
