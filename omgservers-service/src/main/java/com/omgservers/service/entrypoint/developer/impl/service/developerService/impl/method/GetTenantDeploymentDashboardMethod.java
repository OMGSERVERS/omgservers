package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentDashboardMethod {
    Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(GetTenantDeploymentDashboardDeveloperRequest request);
}
