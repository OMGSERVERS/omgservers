package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.getStageMethod;

import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageMethod {
    Uni<GetStageInternalResponse> getStage(GetStageRoutedRequest request);
}
