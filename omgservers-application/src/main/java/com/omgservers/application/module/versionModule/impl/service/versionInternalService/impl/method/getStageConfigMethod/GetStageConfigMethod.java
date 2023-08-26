package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getStageConfigMethod;

import com.omgservers.dto.versionModule.GetStageConfigShardRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageConfigMethod {

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigShardRequest request);
}
