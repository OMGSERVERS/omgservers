package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.tenantDashboard.TenantDashboardModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDashboardResponse {

    @NotNull
    TenantDashboardModel tenantDashboard;
}