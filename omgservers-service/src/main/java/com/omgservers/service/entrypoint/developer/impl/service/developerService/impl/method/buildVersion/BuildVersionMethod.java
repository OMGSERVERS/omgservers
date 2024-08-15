package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.buildVersion;

import com.omgservers.schema.entrypoint.developer.BuildVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.BuildVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface BuildVersionMethod {
    Uni<BuildVersionDeveloperResponse> buildVersion(BuildVersionDeveloperRequest request);
}
