package com.omgservers.module.developer.impl.service.developerService.impl.method.getVersionStatus;

import com.omgservers.dto.developer.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developer.GetVersionStatusDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionStatusMethod {
    Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request);
}
