package com.omgservers.module.tenant.impl.service.versionService.impl.method.findStageVersionId;

import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
import io.smallrye.mutiny.Uni;

public interface FindStageVersionIdMethod {

    Uni<FindStageVersionIdResponse> findStageVersionId(FindStageVersionIdRequest request);
}
