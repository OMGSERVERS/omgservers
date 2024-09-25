package com.omgservers.schema.module.tenant.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.dto.TenantDeploymentDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDeploymentDataResponse {

    TenantDeploymentDataDto tenantDeploymentData;
}
