package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.getStageMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);
}
