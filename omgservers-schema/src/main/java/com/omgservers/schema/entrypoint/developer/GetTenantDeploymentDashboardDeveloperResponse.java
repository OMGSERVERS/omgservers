package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDashboardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDeploymentDashboardDeveloperResponse {

    TenantDeploymentDashboardDto tenantDeploymentDashboard;
}
