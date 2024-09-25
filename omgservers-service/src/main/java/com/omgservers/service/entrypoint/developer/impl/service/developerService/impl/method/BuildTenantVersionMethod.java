package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface BuildTenantVersionMethod {
    Uni<BuildTenantVersionDeveloperResponse> execute(BuildTenantVersionDeveloperRequest request);
}
