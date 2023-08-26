package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getVersionMethod;

import com.omgservers.dto.versionModule.GetVersionShardRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionInternalResponse> getVersion(GetVersionShardRequest request);
}
