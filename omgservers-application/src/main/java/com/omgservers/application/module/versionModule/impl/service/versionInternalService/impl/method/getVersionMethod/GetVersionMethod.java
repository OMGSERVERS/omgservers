package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getVersionMethod;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request);
}
