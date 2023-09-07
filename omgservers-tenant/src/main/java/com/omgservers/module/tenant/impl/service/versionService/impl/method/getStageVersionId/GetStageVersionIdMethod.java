package com.omgservers.module.tenant.impl.service.versionService.impl.method.getStageVersionId;

import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageVersionIdMethod {

    Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request);
}
