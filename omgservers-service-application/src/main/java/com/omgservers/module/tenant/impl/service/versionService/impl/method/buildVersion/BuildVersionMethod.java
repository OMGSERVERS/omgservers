package com.omgservers.module.tenant.impl.service.versionService.impl.method.buildVersion;

import com.omgservers.model.dto.tenant.BuildVersionRequest;
import com.omgservers.model.dto.tenant.BuildVersionResponse;
import io.smallrye.mutiny.Uni;

public interface BuildVersionMethod {
    Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request);
}
