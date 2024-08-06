package com.omgservers.service.entrypoint.developer.impl.operation.mapTenantDataToDashboard;

import com.omgservers.schema.entrypoint.developer.dto.TenantDashboardDto;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;

public interface MapTenantDataToDashboardOperation {
    TenantDashboardDto mapTenantDataToDashboard(TenantDataDto tenantData);
}
