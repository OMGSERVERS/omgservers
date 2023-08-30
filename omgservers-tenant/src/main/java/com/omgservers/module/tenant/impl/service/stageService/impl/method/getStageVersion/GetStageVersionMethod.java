package com.omgservers.module.tenant.impl.service.stageService.impl.method.getStageVersion;

import com.omgservers.dto.tenant.GetStageVersionRequest;
import com.omgservers.dto.tenant.GetStageVersionResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageVersionMethod {
    Uni<GetStageVersionResponse> getStageVersion(GetStageVersionRequest request);
}
