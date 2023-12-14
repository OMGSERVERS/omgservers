package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.getTenantDashboard;

import com.omgservers.model.dto.tenant.GetTenantDashboardRequest;
import com.omgservers.model.dto.tenant.GetTenantDashboardResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDashboardMethod {
    Uni<GetTenantDashboardResponse> getTenantDashboard(GetTenantDashboardRequest request);
}
