package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getStageConfigMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetStageConfigInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageConfigMethod {

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request);
}
