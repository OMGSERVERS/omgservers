package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.getTenantDashboard;

import com.omgservers.schema.module.tenant.GetTenantDashboardRequest;
import com.omgservers.schema.module.tenant.GetTenantDashboardResponse;
import com.omgservers.schema.module.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.GetTenantResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDashboardMethod {
    Uni<GetTenantDashboardResponse> getTenantDashboard(GetTenantDashboardRequest request);
}
