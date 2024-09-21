package com.omgservers.schema.module.tenant.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDeploymentResponse {

    TenantDeploymentModel tenantDeployment;
}
