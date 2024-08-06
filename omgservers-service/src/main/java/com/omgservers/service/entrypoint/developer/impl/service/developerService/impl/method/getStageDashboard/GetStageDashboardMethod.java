package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getStageDashboard;

import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetStageDashboardMethod {
    Uni<GetStageDashboardDeveloperResponse> getStageDashboard(GetStageDashboardDeveloperRequest request);
}
