package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.getStageData;

import com.omgservers.schema.module.tenant.stage.GetStageDataRequest;
import com.omgservers.schema.module.tenant.stage.GetStageDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageDataMethod {
    Uni<GetStageDataResponse> getStageData(GetStageDataRequest request);
}
