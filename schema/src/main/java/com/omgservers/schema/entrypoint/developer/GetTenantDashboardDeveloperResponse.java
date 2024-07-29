package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.model.tenantDashboard.TenantDashboardModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDashboardDeveloperResponse {

    TenantDashboardModel tenantDashboard;
}
