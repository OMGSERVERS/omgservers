package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDashboardMethod {
    Uni<GetTenantDashboardDeveloperResponse> execute(GetTenantDashboardDeveloperRequest request);
}
