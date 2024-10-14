package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDashboardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDashboardDeveloperResponse {

    TenantDashboardDto dashboard;
}
