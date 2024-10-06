package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionDashboardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantVersionDashboardDeveloperResponse {

    TenantVersionDashboardDto tenantVersionDashboard;
}