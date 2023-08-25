package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.getVersionStatusOperation;

import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionStatusMethod {
    Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request);
}
