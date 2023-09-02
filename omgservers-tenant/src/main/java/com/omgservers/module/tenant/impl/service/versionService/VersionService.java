package com.omgservers.module.tenant.impl.service.versionService;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import io.smallrye.mutiny.Uni;

public interface VersionService {

    Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request);
}
