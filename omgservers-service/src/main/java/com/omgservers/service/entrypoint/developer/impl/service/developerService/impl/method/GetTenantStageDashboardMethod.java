package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageDashboardMethod {
    Uni<GetTenantStageDashboardDeveloperResponse> execute(GetTenantStageDashboardDeveloperRequest request);
}
