package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getVersionDashboard;

import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionDashboardMethod {
    Uni<GetVersionDashboardDeveloperResponse> getVersionDashboard(GetVersionDashboardDeveloperRequest request);
}
