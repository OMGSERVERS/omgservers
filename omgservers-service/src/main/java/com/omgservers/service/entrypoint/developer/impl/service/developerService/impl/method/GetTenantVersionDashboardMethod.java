package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionDashboardMethod {
    Uni<GetTenantVersionDashboardDeveloperResponse> execute(GetTenantVersionDashboardDeveloperRequest request);
}
