package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getTenantDashboard;

import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDashboardMethod {
    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(GetTenantDashboardDeveloperRequest request);
}
