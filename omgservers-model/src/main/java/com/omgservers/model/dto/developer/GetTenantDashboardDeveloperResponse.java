package com.omgservers.model.dto.developer;

import com.omgservers.model.tenantDashboard.TenantDashboardModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDashboardDeveloperResponse {

    TenantDashboardModel tenantDashboard;
}
