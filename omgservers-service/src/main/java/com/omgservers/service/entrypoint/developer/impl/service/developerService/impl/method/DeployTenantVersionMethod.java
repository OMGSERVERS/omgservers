package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeployTenantVersionMethod {
    Uni<DeployTenantVersionDeveloperResponse> execute(DeployTenantVersionDeveloperRequest request);
}
