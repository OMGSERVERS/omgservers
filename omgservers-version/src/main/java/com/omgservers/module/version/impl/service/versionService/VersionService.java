package com.omgservers.module.version.impl.service.versionService;

import com.omgservers.dto.version.BuildVersionRequest;
import com.omgservers.dto.version.BuildVersionResponse;
import io.smallrye.mutiny.Uni;

public interface VersionService {

    Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request);
}
