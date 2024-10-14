package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDashboardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantProjectDashboardDeveloperResponse {

    TenantProjectDashboardDto dashboard;
}
