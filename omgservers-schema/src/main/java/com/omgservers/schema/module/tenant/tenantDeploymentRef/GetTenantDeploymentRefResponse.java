package com.omgservers.schema.module.tenant.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDeploymentRefResponse {

    TenantDeploymentRefModel tenantDeploymentRef;
}
