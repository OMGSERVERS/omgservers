package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectDashboardMethod {
    Uni<GetTenantProjectDashboardDeveloperResponse> execute(GetTenantProjectDashboardDeveloperRequest request);
}
