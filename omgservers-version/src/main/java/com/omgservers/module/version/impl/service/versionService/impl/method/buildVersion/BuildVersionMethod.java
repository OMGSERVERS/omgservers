package com.omgservers.module.version.impl.service.versionService.impl.method.buildVersion;

import com.omgservers.dto.version.BuildVersionRequest;
import com.omgservers.dto.version.BuildVersionResponse;
import io.smallrye.mutiny.Uni;

public interface BuildVersionMethod {
    Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request);
}
